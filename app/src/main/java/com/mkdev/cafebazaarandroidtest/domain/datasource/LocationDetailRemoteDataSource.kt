package com.mkdev.cafebazaarandroidtest.domain.datasource

import com.mkdev.cafebazaarandroidtest.domain.BaseApiResponse
import com.mkdev.cafebazaarandroidtest.domain.TestAppAPI
import com.mkdev.cafebazaarandroidtest.domain.model.detail.VenuesDetailResponse
import io.reactivex.Single
import javax.inject.Inject

class LocationDetailRemoteDataSource @Inject constructor(private val api: TestAppAPI) {

    fun getVenueDetails(
        clientId: String,
        clientSecret: String,
        version: Int,
        venueId: String
    ): Single<BaseApiResponse<VenuesDetailResponse>> {

        return api.getVenueDetails(
            venueId,
            clientId,
            clientSecret,
            version
        )
    }
}
