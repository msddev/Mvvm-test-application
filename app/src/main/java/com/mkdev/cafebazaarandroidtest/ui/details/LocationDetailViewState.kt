package com.mkdev.cafebazaarandroidtest.ui.details

import com.mkdev.cafebazaarandroidtest.domain.model.detail.VenueDetail
import com.mkdev.cafebazaarandroidtest.utils.domain.Status

class LocationDetailViewState(
    val status: Status,
    val error: String? = null,
    val data: VenueDetail? = null
) {
    fun getLocationDetail() = data

    fun isLoading() = status == Status.LOADING

    fun getErrorMessage() = error

    fun shouldShowErrorMessage() = error != null
}
