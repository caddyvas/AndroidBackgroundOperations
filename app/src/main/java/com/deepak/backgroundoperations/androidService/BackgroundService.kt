package com.deepak.backgroundoperations.androidService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class BackgroundService : Service() {

    /**
     * Background Service runs when the app is minimized or in the background.Service terminated when
     * App is closed
     *
     * A Service runs on the main thread of the calling Component’s process by default (and hence can degrade
     * responsiveness and cause ANRs), hence you should create a new Thread to perform long running operations.
     * A Service can also be made to run in a completely different process.
     */
    private var isRunning = true
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // create a thread that prints a message every 2 seconds
        Thread {
            while (isRunning) {
                Thread.sleep(1000)
                Log.i(
                    "BackgroundService",
                    "Service Running in the background in Thread: ${Thread.currentThread().name}"
                )
            }
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        Log.d("Background Service", "Service destroyed")
    }
}