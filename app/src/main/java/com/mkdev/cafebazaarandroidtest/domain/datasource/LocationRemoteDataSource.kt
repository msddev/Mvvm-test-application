package com.mkdev.cafebazaarandroidtest.domain.datasource

import com.mkdev.cafebazaarandroidtest.domain.BaseApiResponse
import com.mkdev.cafebazaarandroidtest.domain.TestAppAPI
import com.mkdev.cafebazaarandroidtest.domain.model.explore.VenuesExploreResponse
import io.reactivex.Single
import javax.inject.Inject

class LocationRemoteDataSource @Inject constructor(private val api: TestAppAPI) {

    fun getVenueRecommendations(
        clientId: String,
        clientSecret: String,
        version: Int,
        sortByDistance: Int,
        latitude: Double,
        longitude: Double,
        offset: Int,
        limit: Int
    ): Single<BaseApiResponse<VenuesExploreResponse>> {

        return api.getVenueRecommendations(
            clientId,
            clientSecret,
            version,
            sortByDistance,
            "$latitude, $longitude",
            offset,
            limit
        )
    }
}
