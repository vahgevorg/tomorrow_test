package com.tomorrow.weatherapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.tomorrow.weatherapp.R
import com.tomorrow.weatherapp.core.common.Constants.DELAY_LOCATION_SERVICE_UPDATES
import com.tomorrow.weatherapp.core.common.Locations.locations
import com.tomorrow.weatherapp.domain.model.LocationDomainModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class LocationService : Service() {

    private val locationFlow = MutableSharedFlow<LocationDomainModel>(replay = 1)
    private lateinit var locationUpdatesJob: Job

    override fun onCreate() {
        super.onCreate()
        locationUpdatesJob = startLocationUpdates()
    }

    private fun startLocationUpdates(): Job = CoroutineScope(Dispatchers.IO).launch {
        var currentIndex = 0
        while (isActive) {
            val currentLocation = locations[currentIndex]
            locationFlow.emit(currentLocation)
            currentIndex = (currentIndex + 1) % locations.size
            delay(DELAY_LOCATION_SERVICE_UPDATES)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return LocationBinder()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        locationUpdatesJob.cancel()
    }

    private fun startForegroundService() {
        // Create a notification channel (required for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "location_channel"
            val channelName = "Location Updates"
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }

        // Create a notification for the foreground service
        val notification = NotificationCompat.Builder(this, "location_channel")
            .setContentTitle("Weather App")
            .setContentText("Location updates in progress")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .build()

        // Start the service in the foreground with the notification
        startForeground(1, notification)
    }

    // Public method to allow other components to subscribe to location updates
    fun getLocationUpdates(): Flow<LocationDomainModel> {
        return locationFlow
    }

    inner class LocationBinder : Binder() {
        fun getService(): LocationService = this@LocationService
    }
}
