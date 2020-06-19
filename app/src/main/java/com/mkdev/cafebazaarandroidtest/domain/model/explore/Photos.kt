package com.mkdev.cafebazaarandroidtest.domain.model.explore

import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("groups")
    val groups: List<GroupX>? = listOf()
)