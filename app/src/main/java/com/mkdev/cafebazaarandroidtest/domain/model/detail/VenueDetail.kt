package com.mkdev.cafebazaarandroidtest.domain.model.detail

import android.graphics.Color
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mkdev.cafebazaarandroidtest.db.typeConverters.VenueCategoryTypeConverter
import com.mkdev.cafebazaarandroidtest.db.typeConverters.VenueContactTypeConverter
import com.mkdev.cafebazaarandroidtest.db.typeConverters.VenueLocationTypeConverter
import com.mkdev.cafebazaarandroidtest.db.typeConverters.VenuePhotosTypeConverter
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Category
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Location
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Photos
import com.mkdev.cafebazaarandroidtest.utils.getScreenWidth

@Entity(tableName = "VenueDetail")
data class VenueDetail(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    var id: String = "",
    @TypeConverters(VenueCategoryTypeConverter::class)
    @SerializedName("categories")
    var categories: List<Category>? = listOf(),
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
    var ratingColor: String? = "",
    @SerializedName("timeZone")
    var timeZone: String = "",
    @SerializedName("canonicalUrl")
    var canonicalUrl: String? = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("verified")
    var verified: Boolean = false,
    @TypeConverters(VenuePhotosTypeConverter::class)
    @SerializedName("photos")
    var photos: Photos? = Photos()
) {
    fun getPhoto(): String {
        return photos?.groups?.get(0)?.items?.get(0)?.let {
            it.prefix?.plus(getScreenWidth() / 2)?.plus(it.suffix)
        } ?: run {
            ""
        }
    }

    fun getAddress(): String? {
        return location.formattedAddress?.get(0)
    }

    fun getCategory(): String? {
        return categories?.get(0)?.name
    }

    fun getRatingBackColor(): Int {
        val color = if (!ratingColor.isNullOrEmpty()) {
            ratingColor
        } else {
            "85C5F5"
        }

        return Color.parseColor("#$color")
    }
}