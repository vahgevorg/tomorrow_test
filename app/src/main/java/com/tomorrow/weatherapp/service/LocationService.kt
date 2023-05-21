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

class LocationService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        return LocationBinder()
    }

    private fun startForegroundService() {
        // Create a notification channel (required for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "location_channel"
            val channelName = "Location Updates"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
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

    inner class LocationBinder : Binder() {
        fun getService(): LocationService = this@LocationService
    }
}
