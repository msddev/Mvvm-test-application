package com.mkdev.cafebazaarandroidtest.repo

import NetworkBoundResource
import androidx.lifecycle.LiveData
import com.mkdev.cafebazaarandroidtest.core.Constants.NetworkService.RATE_LIMITER_TYPE
import com.mkdev.cafebazaarandroidtest.domain.BaseApiResponse
import com.mkdev.cafebazaarandroidtest.domain.datasource.LocationDetailLocalDataSource
import com.mkdev.cafebazaarandroidtest.domain.datasource.LocationDetailRemoteDataSource
import com.mkdev.cafebazaarandroidtest.domain.model.detail.VenueDetail
import com.mkdev.cafebazaarandroidtest.domain.model.detail.VenuesDetailResponse
import com.mkdev.cafebazaarandroidtest.utils.domain.RateLimiter
import com.mkdev.cafebazaarandroidtest.utils.domain.Resource
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationDetailRepository @Inject constructor(
    private val locationDetailRemoteDataSource: LocationDetailRemoteDataSource,
    private val locationDetailLocalDataSource: LocationDetailLocalDataSource
) {

    private val rateLimit = RateLimiter<String>(30, TimeUnit.SECONDS)

    fun loadLocationDetailById(
        clientId: String,
        clientSecret: String,
        version: Int,
        venueId: String,
        fetchRequired: Boolean
    ): LiveData<Resource<VenueDetail>> {
        return object :
            NetworkBoundResource<VenueDetail, BaseApiResponse<VenuesDetailResponse>>() {
            override fun saveCallResult(item: BaseApiResponse<VenuesDetailResponse>) {
                locationDetailLocalDataSource.insertVenueDetail(
                    item.response!!.venue
                )
            }

            override fun shouldFetch(data: VenueDetail?): Boolean {
                return (fetchRequired && data == null)
            }


            override fun loadFromDb(): LiveData<VenueDetail> {
                return locationDetailLocalDataSource.getVenueDetailById(venueId)
            }

            override fun createCall(): Single<BaseApiResponse<VenuesDetailResponse>> {
                return locationDetailRemoteDataSource.getVenueDetails(
                    clientId,
                    clientSecret,
                    version,
                    venueId
                )
            }

            override fun onFetchFailed() {
                rateLimit.reset(RATE_LIMITER_TYPE)
            }

        }.asLiveData
    }
}
