package com.mkdev.cafebazaarandroidtest.domain.model.explore

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("reasons")
    val reasons: Reasons = Reasons(),
    @SerializedName("referralId")
    val referralId: String = "",
    @SerializedName("venue")
    val venue: Venue = Venue()
)