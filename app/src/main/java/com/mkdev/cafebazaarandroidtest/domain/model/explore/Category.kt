package com.mkdev.cafebazaarandroidtest.domain.model.explore

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("icon")
    val icon: Icon = Icon(),
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("pluralName")
    val pluralName: String = "",
    @SerializedName("primary")
    val primary: Boolean = false,
    @SerializedName("shortName")
    val shortName: String = ""
) {
    fun getIcon(): String = icon.prefix.plus("64").plus(icon.suffix)
}