package com.mkdev.cafebazaarandroidtest.domain.model.explore

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("cc")
    val cc: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("country")
    val country: String = "",
    @SerializedName("crossStreet")
    val crossStreet: String = "",
    @Ignore
    @SerializedName("formattedAddress")
    val formattedAddress: List<String>? = listOf(),
    @Ignore
    @SerializedName("labeledLatLngs")
    val labeledLatLngs: List<LabeledLatLng> = listOf(),
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lng")
    val lng: Double = 0.0,
    @SerializedName("state")
    val state: String = "",
    @SerializedName("distance")
    val distance: Int = 0
)