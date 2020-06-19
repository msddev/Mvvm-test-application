package com.mkdev.cafebazaarandroidtest.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.mkdev.cafebazaarandroidtest.core.BaseViewModel
import com.mkdev.cafebazaarandroidtest.domain.usecase.LocationDetailUseCase
import javax.inject.Inject

class LocationDetailFragmentViewModel @Inject internal constructor(
    private val locationDetailUseCase: LocationDetailUseCase
) : BaseViewModel() {

    private val _locationDetailParams: MutableLiveData<LocationDetailUseCase.LocationDetailParams> =
        MutableLiveData()

    fun getLocationDetailViewState() = currentWeatherViewState

    private val currentWeatherViewState: LiveData<LocationDetailViewState> =
        _locationDetailParams.switchMap { params ->
            locationDetailUseCase.execute(params)
        }

    fun setLocationDetailParams(params: LocationDetailUseCase.LocationDetailParams) {
        if (_locationDetailParams.value == params)
            return
        _locationDetailParams.postValue(params)
    }

    fun getPhone(): String? {
        return currentWeatherViewState.value?.data?.contact?.phone
    }

    fun getSiteUrl(): String? {
        return currentWeatherViewState.value?.data?.canonicalUrl
    }

    fun getLocationLatLng(): Triple<Double, Double, String> {
        return Triple(
            currentWeatherViewState.value?.data?.location?.lat ?: 0.0,
            currentWeatherViewState.value?.data?.location?.lng ?: 0.0,
            currentWeatherViewState.value?.data?.name ?: ""
        )
    }
}
