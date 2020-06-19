package com.mkdev.cafebazaarandroidtest.domain.model.explore

import com.google.gson.annotations.SerializedName

data class VenuesExploreResponse(
    @SerializedName("groups")
    val groups: List<Group> = listOf(),
    @SerializedName("headerFullLocation")
    val headerFullLocation: String = "",
    @SerializedName("headerLocation")
    val headerLocation: String = "",
    @SerializedName("headerLocationGranularity")
    val headerLocationGranularity: String = "",
    @SerializedName("suggestedRadius")
    val suggestedRadius: Int = 0,
    @SerializedName("totalResults")
    val totalResults: Int = 0
)