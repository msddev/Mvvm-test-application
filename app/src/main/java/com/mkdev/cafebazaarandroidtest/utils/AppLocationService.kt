package com.mkdev.cafebazaarandroidtest.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_SECRET
import com.mkdev.cafebazaarandroidtest.R
import com.mkdev.cafebazaarandroidtest.core.Constants.ServiceAction.START_ACTION
import com.mkdev.cafebazaarandroidtest.utils.extensions.isLocationGpsEnabled
import com.mkdev.cafebazaarandroidtest.utils.extensions.isNetworkAvailable
import timber.log.Timber

const val NOTIFICATION_ID_FOREGROUND_SERVICE = 8466503
const val FOREGROUND_CHANNEL_ID = "com.mkdev.cafebazaarandroidtest.foreground_channel_id"

class AppLocationService : Service(), LocationListener {

    private val locationInterval = 1000
    private val locationDistance = 500f // 500 Meter

    private var intent: Intent? = null
    private var locationManager: LocationManager? = null
    private var notificationManager: NotificationManager? = null

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            if (intent == null) {
                stopLocationService()
                return START_NOT_STICKY
            }

            if (intent.action == START_ACTION) {
                Timber.d(TAG, "Start Service")
                startForeground(NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification())
                startLocationTracker()
            } else {
                stopLocationService()
            }

        } catch (e: Exception) {
            Timber.d(e)
        }

        return START_STICKY
    }

    private fun stopLocationService() {
        Timber.d(TAG, "Stop Service")
        stopForeground(true)
        stopSelf()
    }

    private fun startLocationTracker() {
        if (!isNetworkAvailable() || !isLocationGpsEnabled()) {
            Timber.e(TAG, "GPS and Internet not available!")
            stopLocationService()
            return
        }

        intent = Intent(SERVICE_RECEIVER_STR)
        initializeLocationManager()

        try {
            locationManager?.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                locationInterval.toLong(),
                locationDistance,
                this
            )
        } catch (ex: SecurityException) {
            Timber.e(TAG, "Fail to request location update, $ex")
        } catch (ex: IllegalArgumentException) {
            Timber.e(TAG, "Network provider does not exist, ${ex.message}")
        }

        try {
            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                locationInterval.toLong(),
                locationDistance,
                this
            )
        } catch (ex: SecurityException) {
            Timber.e(TAG, "Fail to request location update, $ex")
        } catch (ex: IllegalArgumentException) {
            Timber.e(TAG, "Gps provider does not exist, ${ex.message}")
        }
    }

    private fun initializeLocationManager() {
        if (locationManager == null) {
            locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE)
                    as LocationManager
        }
    }

    private fun updateLocation(location: Location) {
        intent?.let {
            it.putExtra(LATITUDE_PARAM, location.latitude)
            it.putExtra(LONGITUDE_PARAM, location.longitude)
            sendBroadcast(it)
        }
    }

    override fun onLocationChanged(location: Location) {
        updateLocation(location)
    }

    override fun onProviderDisabled(provider: String) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        updateLocation(Location(provider))
    }

    override fun onDestroy() {
        try {
            locationManager?.removeUpdates(this)
        } catch (ex: Exception) {
            Timber.d(TAG, "Fail to remove location listener, $ex")
        }
        super.onDestroy()
    }

    private fun prepareNotification(): Notification? {
        return try {
            if (
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                && notificationManager?.getNotificationChannel(FOREGROUND_CHANNEL_ID) == null
            ) {
                val name = getString(R.string.location_notify_name)
                val importance = NotificationManager.IMPORTANCE_LOW
                val channel = NotificationChannel(FOREGROUND_CHANNEL_ID, name, importance)

                channel.apply {
                    enableVibration(true)
                    channel.vibrationPattern = longArrayOf(0L)
                    channel.setSound(null, null)
                    lockscreenVisibility = VISIBILITY_SECRET
                }

                notificationManager?.createNotificationChannel(channel)
            }

            // notification builder
            val notificationBuilder: NotificationCompat.Builder =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationCompat.Builder(
                        this,
                        FOREGROUND_CHANNEL_ID
                    )
                } else {
                    NotificationCompat.Builder(this)
                }

            notificationBuilder.apply {
                setContentTitle(getString(R.string.location_notify_name))
                setContentText(getString(R.string.location_notify_state))
                setCategory(NotificationCompat.CATEGORY_SERVICE)
                setOnlyAlertOnce(true)
                setOngoing(true)
                setAutoCancel(false)
                setDefaults(Notification.DEFAULT_VIBRATE)
                setVibrate(longArrayOf(0L))
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setVisibility(VISIBILITY_SECRET)
            }

            notificationBuilder.build()
        } catch (e: Exception) {
            Timber.d(e)
            null
        }
    }

    companion object {
        private const val TAG = "LOCATION_SERVICE"

        var SERVICE_RECEIVER_STR = "com.mkdev.cafebazaarandroidtest.LocationService"
        var LATITUDE_PARAM = "latitude_param"
        var LONGITUDE_PARAM = "longitude_param"
    }
}