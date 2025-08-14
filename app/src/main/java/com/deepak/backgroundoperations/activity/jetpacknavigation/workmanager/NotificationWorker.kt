package com.deepak.backgroundoperations.activity.jetpacknavigation.workmanager

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    // This method will be called by the work manager to perform background task
    override fun doWork(): Result {
        val notification = NotificationCompat.Builder(applicationContext, "default")
            .setSmallIcon(androidx.loader.R.drawable.notification_bg)
            .setContentTitle("Task Completed")
            .setContentText("The background task has been completed through Work Manager!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return Result.failure()
        }
        NotificationManagerCompat.from(applicationContext).notify(1, notification)
        return Result.success()
    }
}