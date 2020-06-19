package com.mkdev.cafebazaarandroidtest.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.mkdev.cafebazaarandroidtest.core.BaseViewModel
import com.mkdev.cafebazaarandroidtest.core.Constants.NetworkService.LAT
import com.mkdev.cafebazaarandroidtest.core.Constants.NetworkService.LON
import com.mkdev.cafebazaarandroidtest.domain.usecase.LocationUseCase
import com.pixplicity.easyprefs.library.Prefs
import javax.inject.Inject

class LocationsFragmentViewModel @Inject internal constructor(
    private val locationUseCase: LocationUseCase
) : BaseViewModel() {

    private val _locationParams: MutableLiveData<LocationUseCase.LocationParams> = MutableLiveData()

    fun getLocationViewState() = currentWeatherViewState

    private val currentWeatherViewState: LiveData<LocationViewState> =
        _locationParams.switchMap { params ->
            locationUseCase.execute(params)
        }

    fun setLocationParams(params: LocationUseCase.LocationParams) {
        if (_locationParams.value == params)
            return
        _locationParams.postValue(params)
    }

    fun saveLatLng(latitude: Double, longitude: Double) {
        Prefs.putDouble(LAT, latitude)
        Prefs.putDouble(LON, longitude)
    }

    fun getLatLng(): Pair<Double, Double> {
        return Pair(Prefs.getDouble(LAT, 0.0), Prefs.getDouble(LON, 0.0))
    }

    fun getLocationTotalResult(): Int? {
        return getLocationViewState().value?.data?.get(0)?.totalResult
    }
}
