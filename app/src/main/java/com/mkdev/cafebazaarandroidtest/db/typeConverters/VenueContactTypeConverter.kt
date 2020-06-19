package com.mkdev.cafebazaarandroidtest.db.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mkdev.cafebazaarandroidtest.domain.model.detail.Contact

class VenueContactTypeConverter {

    @TypeConverter
    fun someObjectToString(obj: Contact?): String? {
        return if (obj == null) null else Gson().toJson(obj)
    }

    @TypeConverter
    fun stringToJsonObject(str: String?): Contact? {
        return if (str == null) null else Gson().fromJson(str, Contact::class.java)
    }
}