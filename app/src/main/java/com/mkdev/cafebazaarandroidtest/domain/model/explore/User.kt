package com.mkdev.cafebazaarandroidtest.domain.model.explore


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("firstName")
    val firstName: String? = "",
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("lastName")
    val lastName: String? = "",
    @SerializedName("photo")
    val photo: Photo? = Photo()
)