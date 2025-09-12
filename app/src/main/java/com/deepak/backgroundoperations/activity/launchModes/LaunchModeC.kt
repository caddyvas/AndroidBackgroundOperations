package com.deepak.backgroundoperations.activity.launchModes

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deepak.backgroundoperations.BGOApplication

class LaunchModeC : AppCompatActivity(), UIUtilsForLaunchModes {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Launch Mode Screen_C"

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    //verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Launch Mode Screen - C",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    IconClickFunction {
                        val intent = Intent(this@LaunchModeC, LaunchModeD::class.java)
                        startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(25.dp))
                    Text(
                        text = BGOApplication.getFormattedStack(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        color = Color.Blue,
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(
                        text = "Task ID: $taskId",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        color = Color(0xFF006800),
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}