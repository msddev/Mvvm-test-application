package com.mkdev.cafebazaarandroidtest.db.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Category
import java.util.*

class VenueCategoryTypeConverter {

    @TypeConverter
    fun someObjectToString(category: List<Category>?): String? {
        return if (category == null) null else Gson().toJson(category)
    }

    @TypeConverter
    fun stringToJsonObject(category: String?): List<Category>? {
        return if (category == null) Collections.emptyList()
        else {
            val listType = object : TypeToken<List<Category>?>() {}.type
            Gson().fromJson(category, listType)
        }
    }
}