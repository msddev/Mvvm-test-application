package com.mkdev.cafebazaarandroidtest.utils.extensions

import android.content.Context
import android.location.LocationManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import timber.log.Timber


fun Context.checkPlayServicesIsInstall(): Boolean {
    return try {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(this)
        result == ConnectionResult.SUCCESS
    } catch (e: Exception) {
        Timber.d(e)
        false
    }
}

fun Context.isLocationGpsEnabled(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}