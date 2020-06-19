package com.mkdev.cafebazaarandroidtest.domain

import com.mkdev.cafebazaarandroidtest.domain.model.detail.VenuesDetailResponse
import com.mkdev.cafebazaarandroidtest.domain.model.explore.VenuesExploreResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TestAppAPI {
    @GET("venues/explore")
    fun getVenueRecommendations(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") version: Int,
        @Query("sortByDistance ") sortByDistance: Int,
        @Query("ll") latLong: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<BaseApiResponse<VenuesExploreResponse>>

    @GET("venues/{VENUE_ID}")
    fun getVenueDetails(
        @Path("VENUE_ID") venueId: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") version: Int
    ): Single<BaseApiResponse<VenuesDetailResponse>>
}
