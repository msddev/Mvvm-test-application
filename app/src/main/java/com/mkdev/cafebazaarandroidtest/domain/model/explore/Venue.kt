package com.mkdev.cafebazaarandroidtest.domain.model.explore

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mkdev.cafebazaarandroidtest.db.typeConverters.VenueCategoryTypeConverter
import com.mkdev.cafebazaarandroidtest.db.typeConverters.VenueContactTypeConverter
import com.mkdev.cafebazaarandroidtest.db.typeConverters.VenueLocationTypeConverter
import com.mkdev.cafebazaarandroidtest.domain.model.detail.Contact

@Entity(tableName = "Venue")
data class Venue(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    var id: String = "",
    @TypeConverters(VenueCategoryTypeConverter::class)
    @SerializedName("categories")
    var categories: List<Category> = listOf(),
    @TypeConverters(VenueContactTypeConverter::class)
    @SerializedName("contact")
    var contact: Contact = Contact(),
    @Ignore
    @SerializedName("createdAt")
    var createdAt: Int = 0,
    @TypeConverters(VenueLocationTypeConverter::class)
    @SerializedName("location")
    var location: Location = Location(),
    @SerializedName("name")
    var name: String = "",
    @SerializedName("rating")
    var rating: Double = 0.0,
    @SerializedName("ratingColor")
    var ratingColor: String = "",
    @SerializedName("timeZone")
    var timeZone: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("verified")
    var verified: Boolean = false,
    @SerializedName("latLng")
    var userLatLng: String = "",
    @SerializedName("totalResult")
    var totalResult: Int = 0
) {
    fun getIcon(): String = categories[0].getIcon()

    fun getCategory(): String = categories[0].name.trim()

    fun getLocationName(): String {
        return name.split("|")[0]
    }

    fun getDistance(): String {
        return if (location.distance <= 1000)
            location.distance.toString().trim().plus(" M")
        else
            (location.distance / 1000F).toString().trim().plus(" KM")
    }
}