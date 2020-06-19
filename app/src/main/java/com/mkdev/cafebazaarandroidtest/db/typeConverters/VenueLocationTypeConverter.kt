package com.mkdev.cafebazaarandroidtest.db.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Location

class VenueLocationTypeConverter {

    @TypeConverter
    fun someObjectToString(obj: Location?): String? {
        return if (obj == null) null else Gson().toJson(obj)
    }

    @TypeConverter
    fun stringToJsonObject(str: String?): Location? {
        return if (str == null) null else Gson().fromJson(str, Location::class.java)
    }
}