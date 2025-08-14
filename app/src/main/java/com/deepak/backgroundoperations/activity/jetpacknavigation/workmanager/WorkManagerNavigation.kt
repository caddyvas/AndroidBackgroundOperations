package com.deepak.backgroundoperations.activity.jetpacknavigation.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.deepak.backgroundoperations.R
import com.deepak.backgroundoperations.fragments.FragmentUtilsBase
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Light Mode", showSystemUi = true)
@Composable
fun WorkManagerNavigationPreview() {
    WorkManagerNavigation(null).DisplayWorkManagerUI()
}

class WorkManagerNavigation(context: Context?) : FragmentUtilsBase {

    override var showDialog = mutableStateOf(false)
    private lateinit var classContext:Context

    init {
        if (context != null) {
            classContext = context
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun InitiateWorkManagerNavigation() {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DisplayWorkManagerUI()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)

    @Composable
    public fun DisplayWorkManagerUI() {
        InfoButton {
            showDialog.value = true
        }

        if (showDialog.value) {
            DisplayAlertDialog(
                "Coroutines",
                stringResource(id = R.string.essential_tools_work_manager_example)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    // kick off the notification time elapse
                    val channel = NotificationChannel(
                        "default",
                        "Default",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    val notificationManager = classContext.getSystemService(NotificationManager::class.java)
                    notificationManager.createNotificationChannel(channel)

                    val notificationWorkRequest: WorkRequest =
                        OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                            .setInitialDelay(1, TimeUnit.MINUTES)
                            .build()
                    Log.i("WorkManager", notificationWorkRequest.toString())

                    // schedule the WorkRequest with WorkManager
                    WorkManager.getInstance(classContext).enqueue(notificationWorkRequest)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Get Notification")
            }
        }
    }
}
