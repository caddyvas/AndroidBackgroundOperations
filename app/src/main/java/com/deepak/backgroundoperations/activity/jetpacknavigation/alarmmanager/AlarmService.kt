package com.deepak.backgroundoperations.activity.jetpacknavigation.alarmmanager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class AlarmService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AlarmService", "AlarmSent")
        return super.onStartCommand(intent, flags, startId)
    }
}