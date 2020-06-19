package com.mkdev.cafebazaarandroidtest.ui.locations.result

import androidx.databinding.ObservableField
import com.mkdev.cafebazaarandroidtest.core.BaseViewModel
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Item
import com.mkdev.cafebazaarandroidtest.domain.model.explore.Venue
import javax.inject.Inject

class LocationResultViewModel @Inject internal constructor() : BaseViewModel() {
    var item = ObservableField<Venue>()
}