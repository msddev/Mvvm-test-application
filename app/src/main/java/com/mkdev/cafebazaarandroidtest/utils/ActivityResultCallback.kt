package com.mkdev.cafebazaarandroidtest.utils

import android.content.Intent

interface ActivityResultCallback {
    fun onResult(requestCode: Int, resultCode: Int, data: Intent?)
}