package com.mkdev.cafebazaarandroidtest.db.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Location
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Photos

class VenuePhotosTypeConverter {

    @TypeConverter
    fun someObjectToString(obj: Photos?): String? {
        return if (obj == null) null else Gson().toJson(obj)
    }

    @TypeConverter
    fun stringToJsonObject(str: String?): Photos? {
        return if (str == null) null else Gson().fromJson(str, Photos::class.java)
    }
}