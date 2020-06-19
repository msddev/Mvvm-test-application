package com.mkdev.cafebazaarandroidtest.repo

import NetworkBoundResource
import androidx.lifecycle.LiveData
import com.mkdev.cafebazaarandroidtest.core.Constants.NetworkService.RATE_LIMITER_TYPE
import com.mkdev.cafebazaarandroidtest.domain.BaseApiResponse
import com.mkdev.cafebazaarandroidtest.domain.datasource.LocationLocalDataSource
import com.mkdev.cafebazaarandroidtest.domain.datasource.LocationRemoteDataSource
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Venue
import com.mkdev.cafebazaarandroidtest.domain.model.explore.VenuesExploreResponse
import com.mkdev.cafebazaarandroidtest.utils.domain.RateLimiter
import com.mkdev.cafebazaarandroidtest.utils.domain.Resource
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationRemoteDataSource: LocationRemoteDataSource,
    private val locationLocalDataSource: LocationLocalDataSource
) {

    private val rateLimit = RateLimiter<String>(30, TimeUnit.SECONDS)

    fun loadLocationsByLatLon(
        clientId: String,
        clientSecret: String,
        version: Int,
        sortByDistance: Int,
        latitude: Double,
        longitude: Double,
        offset: Int,
        limit: Int,
        fetchRequired: Boolean
    ): LiveData<Resource<List<Venue>>> {
        return object :
            NetworkBoundResource<List<Venue>, BaseApiResponse<VenuesExploreResponse>>() {
            override fun saveCallResult(item: BaseApiResponse<VenuesExploreResponse>) {

                val venueList: MutableList<Venue> = mutableListOf()
                item.response!!.groups[0].items.forEach {
                    it.venue.userLatLng = "$latitude, $longitude"
                    it.venue.totalResult = item.response!!.totalResults
                    venueList.add(it.venue)
                }

                locationLocalDataSource.insertVenues(venueList)
            }

            override fun shouldFetch(data: List<Venue>?): Boolean {
                return (fetchRequired && (data.isNullOrEmpty() || (data.isNotEmpty() && offset > 0)))
            }

            override fun loadFromDb(): LiveData<List<Venue>> {
                return locationLocalDataSource.getVenues("$latitude, $longitude")
            }

            override fun createCall(): Single<BaseApiResponse<VenuesExploreResponse>> {
                return locationRemoteDataSource.getVenueRecommendations(
                    clientId,
                    clientSecret,
                    version,
                    sortByDistance,
                    latitude,
                    longitude,
                    offset,
                    limit
                )
            }

            override fun onFetchFailed() {
                rateLimit.reset(RATE_LIMITER_TYPE)
            }

        }.asLiveData
    }
}
