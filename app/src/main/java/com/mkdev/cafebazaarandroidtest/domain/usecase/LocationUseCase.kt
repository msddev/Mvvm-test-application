package com.mkdev.cafebazaarandroidtest.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Venue
import com.mkdev.cafebazaarandroidtest.repo.LocationRepository
import com.mkdev.cafebazaarandroidtest.ui.locations.LocationViewState
import com.mkdev.cafebazaarandroidtest.utils.UseCaseLiveData
import com.mkdev.cafebazaarandroidtest.utils.domain.Resource
import javax.inject.Inject

class LocationUseCase @Inject internal constructor(
    private val repository: LocationRepository
) : UseCaseLiveData<LocationViewState, LocationUseCase.LocationParams,
        LocationRepository>() {

    override fun getRepository(): LocationRepository {
        return repository
    }

    override fun buildUseCaseObservable(params: LocationParams?): LiveData<LocationViewState> {
        return repository.loadLocationsByLatLon(
            clientId = params?.clientId ?: "",
            clientSecret = params?.clientSecret ?: "",
            version = params?.version ?: 0,
            sortByDistance = params?.sortByDistance ?: 0,
            latitude = params?.latitude ?: 0.0,
            longitude = params?.longitude ?: 0.0,
            offset = params?.offset ?: 0,
            limit = params?.limit ?: 0,
            fetchRequired = params?.fetchRequired ?: true
        ).map {
            onCurrentWeatherResultReady(it)
        }
    }

    private fun onCurrentWeatherResultReady(resource: Resource<List<Venue>>): LocationViewState {
        return LocationViewState(
            status = resource.status,
            error = resource.message,
            data = resource.data
        )
    }

    class LocationParams(
        val clientId: String,
        val clientSecret: String,
        val version: Int,
        val sortByDistance: Int = 1,
        val latitude: Double,
        val longitude: Double,
        val offset: Int,
        val limit: Int,
        val fetchRequired: Boolean
    ) : Params()
}
