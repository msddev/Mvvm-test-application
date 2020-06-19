package com.mkdev.cafebazaarandroidtest.domain.model.explore

import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("items")
    val items: List<Item> = listOf(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: String = ""
)