package com.mkdev.cafebazaarandroidtest.domain

import com.google.gson.annotations.SerializedName
import com.mkdev.cafebazaarandroidtest.domain.model.Meta

class BaseApiResponse<T> {
    @SerializedName("meta")
    var meta: Meta? = null
    @SerializedName("response")
    var response: T? = null
}