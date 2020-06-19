package com.mkdev.cafebazaarandroidtest.domain.model.explore


import com.google.gson.annotations.SerializedName

data class ItemXX(
    @SerializedName("createdAt")
    val createdAt: Int? = 0,
    @SerializedName("height")
    val height: Int? = 0,
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("prefix")
    val prefix: String? = "",
    @SerializedName("source")
    val source: Source? = Source(),
    @SerializedName("suffix")
    val suffix: String? = "",
    @SerializedName("user")
    val user: User? = User(),
    @SerializedName("visibility")
    val visibility: String? = "",
    @SerializedName("width")
    val width: Int? = 0
)