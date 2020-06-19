package com.mkdev.cafebazaarandroidtest.ui.locations

import com.mkdev.cafebazaarandroidtest.domain.model.explore.Venue
import com.mkdev.cafebazaarandroidtest.utils.domain.Status

class LocationViewState(
    val status: Status,
    val error: String? = null,
    val data: List<Venue>? = null
) {
    fun getLocations() = data

    fun isLoading() = status == Status.LOADING

    fun getErrorMessage() = error

    fun shouldShowErrorMessage() = error != null
}
