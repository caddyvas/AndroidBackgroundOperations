package com.deepak.backgroundoperations.activity.launchModes

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deepak.backgroundoperations.R
import com.deepak.backgroundoperations.activity.BackgroundActivity
import com.deepak.backgroundoperations.activity.CoroutineActivity
import com.deepak.backgroundoperations.activity.MainActivity
import com.deepak.backgroundoperations.fragments.FragmentUtilsBase

class LaunchModesActivity : AppCompatActivity(),
    FragmentUtilsBase {
    override var showDialog = mutableStateOf(false)
    private var showAnotherDialog = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Launch Modes"

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                LaunchModesPageElements()
            }
        }
    }

    @Composable
    fun LaunchModesPageElements() {
        InfoButton {
            showDialog.value = true
        }

        InfoButtonTwo {
            showAnotherDialog.value = true
        }

        if (showDialog.value) {
            DisplayAlertDialog("Launch Modes", stringResource(id = R.string.launch_mode))
        }

        if (showAnotherDialog.value) {
            DisplayAnotherAlertDialog(
                title = "Flag Types",
                content = stringResource(id = R.string.launch_mode_flags)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    openActivity(LaunchModesActivity.LAUNCH_MODE_A)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "ACTIVITY A")
            }
            Button(
                onClick = {
                    openActivity(LaunchModesActivity.LAUNCH_MODE_B)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "ACTIVITY B")
            }
            Button(
                onClick = {
                    openActivity(LaunchModesActivity.LAUNCH_MODE_C)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "ACTIVITY C")
            }
            Button(
                onClick = {
                    openActivity(LaunchModesActivity.LAUNCH_MODE_D)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "ACTIVITY D")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.launchmode_std),
                    contentDescription = "Image 1",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(1.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.launchmode_stop),
                    contentDescription = "Image 2",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(1.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.launchmode_stask),
                    contentDescription = "Image 3",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(1.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.launchmode_sins),
                    contentDescription = "Image 4",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(1.dp)
                )
            }
        }
    }

    @Composable
    fun InfoButtonTwo(onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(5.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(45.dp)
                        .padding(2.dp),
                    imageVector = Icons.Filled.Info,
                    contentDescription = "InfoTwo",
                    tint = colorResource(id = R.color.teal_200)
                )
            }
        }
    }

    @Preview(name = "Light Mode", showSystemUi = true)
    @Composable
    fun PreviewOfLaunchMode() {
        LaunchModesPageElements()
    }

    // this is needed just for this class, not a good practice
    @Composable
    fun DisplayAnotherAlertDialog(title: String, content: String) {
        AlertDialog(onDismissRequest = { showAnotherDialog.value = false },
            confirmButton = {
                TextButton(onClick = { showAnotherDialog.value = false }) {
                    Text("OK")
                }
            },
            title = {
                Text(text = title)
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(text = content)
                } // comes from strings.xml
            }
        )
    }

    private fun openActivity(activityToOpen: Int) {
        var intent: Intent? = null
        when (activityToOpen) {
            LaunchModesActivity.LAUNCH_MODE_A -> {
                intent = Intent(this@LaunchModesActivity, LaunchModeA::class.java)
            }

            LaunchModesActivity.LAUNCH_MODE_B -> {
                intent = Intent(this@LaunchModesActivity, LaunchModeB::class.java)
            }

            LaunchModesActivity.LAUNCH_MODE_C -> {
                intent = Intent(this@LaunchModesActivity, LaunchModeC::class.java)
            }

            LaunchModesActivity.LAUNCH_MODE_D -> {
                intent = Intent(this@LaunchModesActivity, LaunchModeD::class.java)
            }

        }
        startActivity(intent)
    }

    companion object Activities {
        const val LAUNCH_MODE_A: Int = 2001
        const val LAUNCH_MODE_B: Int = 2002
        const val LAUNCH_MODE_C: Int = 2003
        const val LAUNCH_MODE_D: Int = 2004
    }
}
