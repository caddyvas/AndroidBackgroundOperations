package com.deepak.backgroundoperations.androidService

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.deepak.backgroundoperations.R

/**
 * Foreground Service runs even when the app is minimized or in the background or "terminated".
 *
 * You can also start the foreground service through broadcast receiver when the app
 * is restarted / reboot. Create a broadcast receiver, "receive" the ACTION_BOOT_COMPLETED
 * event and start the foreground in onReceive() of Broadcast receiver
 */
class ForegroundService : Service() {

    private var isRunning = true

    @SuppressLint("ForegroundServiceType")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // create a thread that prints a message every 2 seconds
        Thread {
            while (isRunning) {
                Thread.sleep(2000)
                Log.i("ForegroundService", "Service Running in the background")
            }
        }.start()
        val channelId: String = "Foreground Service ID"
        val notificationChannel = NotificationChannel(
            channelId, channelId,
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(
            notificationChannel
        )
        val notification: Notification.Builder =
            Notification.Builder(this, channelId).setContentText("Service is running!")
                .setContentTitle("SERVICE ENABLED").setSmallIcon(R.drawable.ic_launcher_background)
        // we must let the user know that this service runs even when the app is terminated
        startForeground(1001, notification.build())
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        Log.i("Foreground Services", "Foreground services Stopped in onDestroy()")
    }
}