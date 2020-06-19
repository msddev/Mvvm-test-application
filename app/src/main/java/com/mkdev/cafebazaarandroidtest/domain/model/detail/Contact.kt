package com.mkdev.cafebazaarandroidtest.domain.model.detail

import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("formattedPhone")
    val formattedPhone: String = "",
    @SerializedName("phone")
    val phone: String = ""
)