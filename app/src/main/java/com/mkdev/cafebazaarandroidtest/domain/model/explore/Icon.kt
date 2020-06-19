package com.mkdev.cafebazaarandroidtest.domain.model.explore

import com.google.gson.annotations.SerializedName

data class Icon(
    @SerializedName("prefix")
    val prefix: String = "",
    @SerializedName("suffix")
    val suffix: String = ""
)