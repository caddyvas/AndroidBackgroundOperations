package com.deepak.backgroundoperations.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.deepak.backgroundoperations.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

class CoroutineDispatchers : Fragment(), FragmentUtilsBase {

    private val dynText = mutableStateOf("")
    override var showDialog = mutableStateOf(false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DispatcherComponents()
                }
            }
        }
    }

    @Preview(name = "Light Mode", showSystemUi = true)
    @Composable
    private fun DispatcherComponents() {

        val context = LocalContext.current

        InfoButton {
            showDialog.value = true
        }

        if (showDialog.value) {
            DisplayAlertDialog(
                "Dispatchers",
                stringResource(id = R.string.coroutine_dispatcher_explanation)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Button(
                onClick = {
                    textDisplayForMainDispatcher(
                        context.getString(R.string.cor_dispatchers_io),
                        "io"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Dispatcher.IO")
            }
            Button(
                onClick = {
                    textDisplayForMainDispatcher(
                        context.getString(R.string.cor_dispatchers_default),
                        "default"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Dispatcher.Default")
            }
            Button(
                onClick = {
                    textDisplayForMainDispatcher(
                        context.getString(R.string.cor_dispatchers_unconfined),
                        "unconfined"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Dispatcher.Unconfined")
            }
            Button(
                onClick = {
                    textDisplayForMainDispatcher(
                        context.getString(R.string.cor_dispatchers_main), "main"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Dispatcher.Main")
            }
            Button(
                onClick = {
                    textDisplayForMainDispatcher(
                        context.getString(R.string.cor_coroutine_context),
                        "coroutineContext"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "CoroutineContext")
            }
            val textToDisplay by dynText
            Text(
                text = textToDisplay,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()),
                style = MaterialTheme.typography.displaySmall,
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }

    /**
     * Special condition for Main dispatcher as it creates a deadlock condition when operated with
     * runBlocking
     */
    private fun textDisplayForMainDispatcher(messageToReturn: String, typeOfDispatcher: String) {
        var messageOne = ""
        var messageTwo = ""
        var messageThree = ""
        when (typeOfDispatcher) {
            "io" -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    messageOne = "\nRUNS IN: ${Thread.currentThread().name}"
                    messageTwo = "\nADDING DELAY"
                    delay(100)
                    messageThree = "\nAFTER DELAY, RUNS IN: ${Thread.currentThread().name}"
                    dynText.value =  messageToReturn + messageOne + messageTwo + messageThree
                }
            }

            "default" -> {
                lifecycleScope.launch(Dispatchers.Default) {
                    messageOne = "\nRUNS IN: ${Thread.currentThread().name}"
                    messageTwo = "\nADDING DELAY"
                    delay(100)
                    messageThree = "\nAFTER DELAY, RUNS IN: ${Thread.currentThread().name}"
                    dynText.value =  messageToReturn + messageOne + messageTwo + messageThree
                }
            }

            "unconfined" -> {
                lifecycleScope.launch(Dispatchers.Unconfined) {
                    messageOne = "\nRUNS IN: ${Thread.currentThread().name}"
                    messageTwo = "\nADDING DELAY"
                    delay(100)
                    messageThree = "\nAFTER DELAY, RUNS IN: ${Thread.currentThread().name}"
                    dynText.value =  messageToReturn + messageOne + messageTwo + messageThree
                }
            }

            "main" -> {
                lifecycleScope.launch(Dispatchers.Main) {
                    messageOne = "\nRUNS IN: ${Thread.currentThread().name}"
                    messageTwo = "\nADDING DELAY"
                    delay(100)
                    messageThree = "\nAFTER DELAY, RUNS IN: ${Thread.currentThread().name}"
                    dynText.value =  messageToReturn + messageOne + messageTwo + messageThree
                }
            }

            "coroutineContext" -> {
                lifecycleScope.launch {
                    launch(coroutineContext) {
                        messageOne = "\nRUNS IN: ${Thread.currentThread().name}"
                        messageTwo = "\nADDING DELAY"
                        delay(100)
                        messageThree = "\nAFTER DELAY, RUNS IN: ${Thread.currentThread().name}"
                        dynText.value =  messageToReturn + messageOne + messageTwo + messageThree
                    }
                }
            }
        }
    }
}