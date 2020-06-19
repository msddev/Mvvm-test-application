package com.mkdev.cafebazaarandroidtest.domain.model.detail

import com.google.gson.annotations.SerializedName

data class VenuesDetailResponse(
    @SerializedName("venue")
    val venue: VenueDetail = VenueDetail()
)