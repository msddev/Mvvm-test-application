package com.mkdev.cafebazaarandroidtest.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mkdev.cafebazaarandroidtest.domain.model.detail.VenueDetail
import com.mkdev.cafebazaarandroidtest.repo.LocationDetailRepository
import com.mkdev.cafebazaarandroidtest.ui.details.LocationDetailViewState
import com.mkdev.cafebazaarandroidtest.utils.UseCaseLiveData
import com.mkdev.cafebazaarandroidtest.utils.domain.Resource
import javax.inject.Inject

class LocationDetailUseCase @Inject internal constructor(
    private val repository: LocationDetailRepository
) : UseCaseLiveData<LocationDetailViewState, LocationDetailUseCase.LocationDetailParams,
        LocationDetailRepository>() {

    override fun getRepository(): LocationDetailRepository {
        return repository
    }

    override fun buildUseCaseObservable(params: LocationDetailParams?): LiveData<LocationDetailViewState> {
        return repository.loadLocationDetailById(
            clientId = params?.clientId ?: "",
            clientSecret = params?.clientSecret ?: "",
            version = params?.version ?: 0,
            venueId = params?.venueId ?: "",
            fetchRequired = params?.fetchRequired ?: true
        ).map {
            onCurrentWeatherResultReady(it)
        }
    }

    private fun onCurrentWeatherResultReady(resource: Resource<VenueDetail>): LocationDetailViewState {
        return LocationDetailViewState(
            status = resource.status,
            error = resource.message,
            data = resource.data
        )
    }

    class LocationDetailParams(
        val clientId: String,
        val clientSecret: String,
        val version: Int = 20181123,
        val venueId: String,
        val fetchRequired: Boolean
    ) : Params()
}
