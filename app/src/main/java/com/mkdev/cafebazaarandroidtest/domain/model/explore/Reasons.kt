package com.mkdev.cafebazaarandroidtest.domain.model.explore

import com.google.gson.annotations.SerializedName

data class Reasons(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("items")
    val items: List<ItemX> = listOf()
)