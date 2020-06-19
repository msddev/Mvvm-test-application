package com.mkdev.cafebazaarandroidtest.ui.main

import androidx.databinding.ObservableField
import com.mkdev.cafebazaarandroidtest.core.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject internal constructor() : BaseViewModel() {
    var toolbarTitle: ObservableField<String> = ObservableField()
}
