package com.mkdev.cafebazaarandroidtest.ui.locations

import android.Manifest
import android.content.*
import android.location.Location
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.mkdev.cafebazaarandroidtest.R
import com.mkdev.cafebazaarandroidtest.core.BaseFragment
import com.mkdev.cafebazaarandroidtest.core.Constants.NetworkService.FOURSQUARE_CLIENT_ID
import com.mkdev.cafebazaarandroidtest.core.Constants.NetworkService.FOURSQUARE_CLIENT_SECRET
import com.mkdev.cafebazaarandroidtest.core.Constants.NetworkService.FOURSQUARE_CLIENT_VERSION
import com.mkdev.cafebazaarandroidtest.core.Constants.NetworkService.PAGE_LIMIT
import com.mkdev.cafebazaarandroidtest.core.Constants.ServiceAction.START_ACTION
import com.mkdev.cafebazaarandroidtest.core.Constants.ServiceAction.STOP_ACTION
import com.mkdev.cafebazaarandroidtest.databinding.FragmentLocationsBinding
import com.mkdev.cafebazaarandroidtest.di.Injectable
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Venue
import com.mkdev.cafebazaarandroidtest.domain.usecase.LocationUseCase
import com.mkdev.cafebazaarandroidtest.ui.dialog.LoadingDialog
import com.mkdev.cafebazaarandroidtest.ui.locations.result.LocationResultAdapter
import com.mkdev.cafebazaarandroidtest.ui.main.MainActivity
import com.mkdev.cafebazaarandroidtest.utils.ActivityResultCallback
import com.mkdev.cafebazaarandroidtest.utils.AppLocationService
import com.mkdev.cafebazaarandroidtest.utils.PaginationScrollListener
import com.mkdev.cafebazaarandroidtest.utils.extensions.*
import com.mkdev.cafebazaarandroidtest.utils.permissionManager.askPermission
import com.mkdev.cafebazaarandroidtest.utils.permissionManager.hasPermission
import io.reactivex.disposables.Disposable
import timber.log.Timber

const val LOCATIONS_FRAGMENT_GPS_REQUEST_CODE = 1003

class LocationsFragment : BaseFragment<LocationsFragmentViewModel, FragmentLocationsBinding>
    (LocationsFragmentViewModel::class.java), Injectable {

    private var disposable: Disposable? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var offset: Int = 0

    override fun getLayoutRes() = R.layout.fragment_locations

    override fun initViewModel() {
        mBinding.viewModel = viewModel
    }

    override fun init() {
        super.init()
        initAdapter()

        if (latitude == 0.0 || longitude == 0.0) {
            checkLocationPermission()
        } else {
            setViewModelObserver()
        }
    }

    private fun initAdapter() {
        val adapter = LocationResultAdapter { item ->
            findNavController().navigate(
                LocationsFragmentDirections.actionLocationsFragmentToLocationDetailFragment(item.id)
            )
        }

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        mBinding.recyclerView.adapter = adapter
        mBinding.recyclerView.layoutManager = layoutManager

        mBinding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                isLastPage = viewModel.getLocationTotalResult()?.let {
                    layoutManager.itemCount >= it
                } ?: run {
                    false
                }

                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                if (isLastPage) {
                    return
                }
                isLoading = true
                offset = layoutManager.itemCount
                viewModel.setLocationParams(getParams())
            }
        })
    }

    private fun initLocationResultAdapter(list: List<Venue>) {
        (mBinding.recyclerView.adapter as LocationResultAdapter).submitList(list)
        isLoading = false
    }

    private fun setViewModelObserver() {
        viewModel.setLocationParams(getParams())

        viewModel.getLocationViewState().observeWith(viewLifecycleOwner) {
            mBinding.viewState = it
            it.data?.let { results -> initLocationResultAdapter(results) }
        }
    }

    private fun getParams(): LocationUseCase.LocationParams {
        return LocationUseCase.LocationParams(
            clientId = FOURSQUARE_CLIENT_ID,
            clientSecret = FOURSQUARE_CLIENT_SECRET,
            version = FOURSQUARE_CLIENT_VERSION,
            latitude = latitude,
            longitude = longitude,
            offset = offset,
            limit = PAGE_LIMIT,
            fetchRequired = requireContext().isNetworkAvailable()
        )
    }

    private fun showAlert(@StringRes message: Int) {
        mBinding.rootView.snack(
            getString(message),
            Snackbar.LENGTH_LONG
        ) {}
    }

    private val locationServiceBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {

            val currentLocation = Location("currentLocation").apply {
                latitude = viewModel.getLatLng().first
                longitude = viewModel.getLatLng().second
            }

            val newLocation = Location("newLocation").apply {
                latitude = intent.getDoubleExtra(AppLocationService.LATITUDE_PARAM, 0.0)
                longitude = intent.getDoubleExtra(AppLocationService.LONGITUDE_PARAM, 0.0)
            }

            val distanceBetween = currentLocation.distanceTo(newLocation)

            //if distance large than 500 meter - get new data
            if (distanceBetween > 500) {
                latitude = newLocation.latitude
                longitude = newLocation.longitude

                viewModel.saveLatLng(latitude, longitude)
            } else {
                latitude = currentLocation.latitude
                longitude = currentLocation.longitude
            }

            loadingDialog.hideDialog()

            if (latitude == 0.0 || longitude == 0.0) {
                showAlert(R.string.current_location_not_found)
                return
            }

            setViewModelObserver()
        }
    }

    private fun updateLocationService(serviceAction: String) {
        val intent = Intent(requireContext(), AppLocationService::class.java)
        intent.action = serviceAction
        requireContext().startService(intent)
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            locationServiceBroadcastReceiver,
            IntentFilter(AppLocationService.SERVICE_RECEIVER_STR)
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(locationServiceBroadcastReceiver)
    }

    override fun onDestroy() {
        mBinding.recyclerView.clearOnScrollListeners()
        disposable?.dispose()
        updateLocationService(STOP_ACTION)
        super.onDestroy()
    }

    /**
     * GPS and Location tracker
     */
    private val TAG = "LOCATIONS_SERVICE"
    private var permissionDialog: AlertDialog? = null
    private var locationRequest: LocationRequest? = null
    private val loadingDialog by lazy {
        LoadingDialog(requireContext(), false)
    }

    private fun checkLocationPermission() {
        try {
            if (!requireContext().checkPlayServicesIsInstall()) {
                showAlert(R.string.google_play_services_is_not_installed)
                return
            }

            if (permissionDialog?.isShowing != true) {

                loadingDialog.showDialog()

                if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    || !hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                ) {
                    permissionDialog =
                        MaterialAlertDialogBuilder(
                            requireContext(),
                            R.style.MaterialAlertDialogTheme
                        )
                            .setMessage(
                                getString(
                                    R.string.permission_message,
                                    getString(R.string.permission_location_message),
                                    getString(R.string.permission_location)
                                )
                            )
                            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                                callPermissionRequest()
                            }
                            .setNegativeButton(getString(R.string.reject)) { dialog, _ ->
                                loadingDialog.hideDialog()
                                dialog.dismiss()
                            }
                            .setCancelable(false)
                            .show()
                } else {
                    enableGpsRequest()
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            showAlert(R.string.error)
        }
    }

    private fun callPermissionRequest() {
        try {
            askPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) {
                enableGpsRequest()
            }.onDeclined { e ->
                loadingDialog.hideDialog()

                if (e.hasDenied() or e.hasForeverDenied()) {
                    MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialogTheme)
                        .setMessage(
                            getString(
                                R.string.show_app_setting,
                                getString(R.string.permission_location)
                            )
                        )
                        .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                            e.goToSettings()
                            dialog.dismiss()
                        }
                        .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setCancelable(false)
                        .show()
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            showAlert(R.string.error)
        }
    }

    private fun enableGpsRequest() {
        try {
            val googleApiClient = GoogleApiClient.Builder(requireContext())
                .addApi(LocationServices.API)
                .build()
            googleApiClient.connect()

            locationRequest = LocationRequest.create()
            locationRequest?.apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 10 * 1000
                fastestInterval = 10000 / 2.toLong()
            }
            val settingsBuilder =
                LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
            settingsBuilder.setAlwaysShow(true)

            val result = LocationServices.getSettingsClient(requireContext())
                .checkLocationSettings(settingsBuilder.build())

            result.addOnCompleteListener { task ->
                try {
                    val response: LocationSettingsResponse? =
                        task.getResult(ApiException::class.java)
                    Timber.i("$TAG All location settings are satisfied.")

                    requireActivity().runOnUiThread {
                        updateLocationService(START_ACTION)
                    }

                } catch (ex: ApiException) {
                    when (ex.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            Timber.i("$TAG Location settings are not satisfied. Show the user a dialog to upgrade location settings")

                            try {
                                val resolvableApiException: ResolvableApiException =
                                    ex as ResolvableApiException

                                (activity as MainActivity).requestActivityResult(
                                    resolvableApiException = resolvableApiException,
                                    activityResultCode = LOCATIONS_FRAGMENT_GPS_REQUEST_CODE,
                                    activityResultCallback = object : ActivityResultCallback {
                                        override fun onResult(
                                            requestCode: Int,
                                            resultCode: Int,
                                            data: Intent?
                                        ) {
                                            if (requireContext().isLocationGpsEnabled()) {
                                                loadingDialog.showDialog()

                                                requireActivity().runOnUiThread {
                                                    updateLocationService(START_ACTION)
                                                }
                                            }
                                        }
                                    })

                            } catch (e: IntentSender.SendIntentException) {
                                Timber.i("$TAG PendingIntent unable to execute request.")
                                Timber.i(e)
                                showAlert(R.string.error)
                            }
                        }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            Timber.i("$TAG Location settings are inadequate, and cannot be fixed here. Dialog not created.")
                            showAlert(R.string.error)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            showAlert(R.string.error)
        }
    }
}