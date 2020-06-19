package com.mkdev.cafebazaarandroidtest.domain.model.explore


import com.google.gson.annotations.SerializedName

data class GroupX(
    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("items")
    val items: List<ItemXX?>? = listOf(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("type")
    val type: String? = ""
)