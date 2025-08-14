package com.deepak.backgroundoperations.activity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.deepak.backgroundoperations.activity.jetpacknavigation.NavHostGraph
import com.deepak.backgroundoperations.androidService.ForegroundService

class MainActivity : AppCompatActivity() {
    private var initiateNavigationControllerState = mutableStateOf(false)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                if (initiateNavigationControllerState.value) {
                    // using Jetpack Navigation for other Background operations
                    val navHostController = rememberNavController()
                    NavHostGraph(this, navHostController, initiateNavigationControllerState)
                } else {
                    UiPageElements()
                }
            }
        }
    }

    @Composable
    fun UiPageElements() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    openActivity(BACKGROUND_SERVICE)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "BACKGROUND SERVICE")
            }
            Button(
                onClick = {
                    openActivity(FOREGROUND_SERVICE)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "FOREGROUND SERVICE")
            }
            Button(
                onClick = { openActivity(HANDLER) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "HANDLER")
            }
            Button(
                onClick = { openActivity(COROUTINE) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "COROUTINE")
            }
            Button(
                onClick = {
                    initiateNavigationControllerState.value = true
                }, // this part deals with navigation controller
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "ESSENTIAL TOOLS")
            }
        }
    }

    @Preview(name = "Light Mode", showSystemUi = true)
    @Composable
    fun PreviewOfUiPageElements() {
        UiPageElements()
    }

    private fun openActivity(activityToOpen: Int) {
        val intent: Intent
        when (activityToOpen) {
            BACKGROUND_SERVICE -> {
                intent = Intent(this@MainActivity, BackgroundActivity::class.java)
                intent.putExtra("activityType", BACKGROUND_SERVICE)
                startActivity(intent)
            }

            FOREGROUND_SERVICE -> {
                intent = Intent(this@MainActivity, BackgroundActivity::class.java)
                intent.putExtra("activityType", FOREGROUND_SERVICE)
                startActivity(intent)
            }

            HANDLER -> {
                intent = Intent(this@MainActivity, BackgroundActivity::class.java)
                intent.putExtra("activityType", HANDLER)
                startActivity(intent)
            }

            COROUTINE -> {
                intent = Intent(this@MainActivity, CoroutineActivity::class.java)
                startActivity(intent)
            }
        }
    }

    /**
     * Add a method to find if there are any other foreground service is alive.
     * When the app is relaunched, if foreground service is already running,
     * another service will be ran on top of the previous
     */
    private fun foregroundServiceRunning(): Boolean {
        val activityManager: ActivityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (serviceList in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (ForegroundService::class.java.name.equals(serviceList.service.className)) {
                return true
            }
        }
        return false
    }

    companion object Activities {
        const val BACKGROUND_SERVICE: Int = 1001
        const val FOREGROUND_SERVICE: Int = 1002
        const val HANDLER: Int = 1003
        const val COROUTINE: Int = 1004
    }
}


