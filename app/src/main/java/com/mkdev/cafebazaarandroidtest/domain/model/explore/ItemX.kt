package com.mkdev.cafebazaarandroidtest.domain.model.explore

import com.google.gson.annotations.SerializedName

data class ItemX(
    @SerializedName("reasonName")
    val reasonName: String = "",
    @SerializedName("summary")
    val summary: String = "",
    @SerializedName("type")
    val type: String = ""
)