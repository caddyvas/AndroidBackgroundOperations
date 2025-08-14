package com.deepak.backgroundoperations.activity.jetpacknavigation.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.deepak.backgroundoperations.R
import com.deepak.backgroundoperations.fragments.FragmentUtilsBase

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Light Mode", showSystemUi = true)
@Composable
fun AlarmManagerNavigationPreview() {
    AlarmManagerNavigation(null).DisplayAlarmManagerUI()
}

class AlarmManagerNavigation(context: Context?) : FragmentUtilsBase {

    override var showDialog = mutableStateOf(false)
    private lateinit var classContext: Context

    init {
        if (context != null) {
            classContext = context
        }
    }

    private val alarmManager = classContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun InitiateAlarmManagerNavigation() {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DisplayAlarmManagerUI()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    public fun DisplayAlarmManagerUI() {
        InfoButton {
            showDialog.value = true
        }

        if (showDialog.value) {
            DisplayAlertDialog(
                "Alarm Manager",
                stringResource(id = R.string.essential_tools_alarm_manager_example)
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
                    val pendingIntent = PendingIntent.getBroadcast(
                        classContext,
                        1,
                        Intent(classContext, AlarmReceiver::class.java),
                        PendingIntent.FLAG_IMMUTABLE
                    )
                    val fiveSeconds = 1000 * 5
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        SystemClock.elapsedRealtime() + fiveSeconds,
                        pendingIntent
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Set Alarm")
            }

            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    val pendingIntent = PendingIntent.getBroadcast(
                        classContext,
                        1,
                        Intent(classContext, AlarmReceiver::class.java),
                        PendingIntent.FLAG_IMMUTABLE
                    )
                    alarmManager.cancel(pendingIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Cancel Alarm")
            }
        }
    }
}