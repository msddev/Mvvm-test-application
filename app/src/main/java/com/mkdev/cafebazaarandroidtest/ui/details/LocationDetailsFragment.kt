package com.mkdev.cafebazaarandroidtest.ui.details

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.annotation.StringRes
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mkdev.cafebazaarandroidtest.R
import com.mkdev.cafebazaarandroidtest.core.BaseFragment
import com.mkdev.cafebazaarandroidtest.core.Constants
import com.mkdev.cafebazaarandroidtest.core.Constants.NetworkService.FOURSQUARE_CLIENT_ID
import com.mkdev.cafebazaarandroidtest.core.Constants.NetworkService.FOURSQUARE_CLIENT_SECRET
import com.mkdev.cafebazaarandroidtest.databinding.FragmentDetailsBinding
import com.mkdev.cafebazaarandroidtest.di.Injectable
import com.mkdev.cafebazaarandroidtest.domain.usecase.LocationDetailUseCase
import com.mkdev.cafebazaarandroidtest.utils.extensions.isNetworkAvailable
import com.mkdev.cafebazaarandroidtest.utils.extensions.observeWith
import com.mkdev.cafebazaarandroidtest.utils.extensions.snack
import kotlinx.android.synthetic.main.fragment_details.*


class LocationDetailsFragment :
    BaseFragment<LocationDetailFragmentViewModel, FragmentDetailsBinding>
        (LocationDetailFragmentViewModel::class.java), Injectable, View.OnClickListener {

    private val locationDetailsFragmentArgs: LocationDetailsFragmentArgs by navArgs()

    override fun getLayoutRes() = R.layout.fragment_details

    override fun initViewModel() {
        mBinding.viewModel = viewModel
    }

    override fun init() {
        super.init()

        viewModel.setLocationDetailParams(
            LocationDetailUseCase.LocationDetailParams(
                clientId = FOURSQUARE_CLIENT_ID,
                clientSecret = FOURSQUARE_CLIENT_SECRET,
                version = Constants.NetworkService.FOURSQUARE_CLIENT_VERSION,
                venueId = locationDetailsFragmentArgs.venueId,
                fetchRequired = requireContext().isNetworkAvailable()
            )
        )

        viewModel.getLocationDetailViewState().observeWith(viewLifecycleOwner) {
            mBinding.viewState = it
        }

        mBinding.ivPhone.setOnClickListener(this)
        mBinding.ivMap.setOnClickListener(this)
        mBinding.ivSite.setOnClickListener(this)
    }

    private fun openPhoneIntent() {
        val phone = viewModel.getPhone()

        if (phone.isNullOrEmpty()) {
            showAlert(R.string.phone_number_not_valid)
            return
        }

        val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(callIntent)
    }

    private fun openLocationSite() {
        val locationSite = viewModel.getSiteUrl()

        if (locationSite.isNullOrEmpty()) {
            showAlert(R.string.url_not_valid)
            return
        }

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(locationSite)
        startActivity(intent)
    }

    private fun openLocationInMap() {
        val locationData = viewModel.getLocationLatLng()

        if (locationData.first == 0.0 || locationData.second == 0.0) {
            showAlert(R.string.address_not_valid)
            return
        }

        val geoUri =
            "http://maps.google.com/maps?q=loc:${locationData.first},${locationData.second} (${locationData.third})"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
        startActivity(intent)
    }

    private fun showAlert(@StringRes message: Int) {
        mBinding.rootView.snack(
            getString(message),
            Snackbar.LENGTH_LONG
        ) {}
    }

    override fun onClick(view: View) {
        when (view) {
            ivPhone -> {
                openPhoneIntent()
            }
            ivMap -> {
                openLocationInMap()
            }
            ivSite -> {
                openLocationSite()
            }
        }
    }
}