package com.deepak.backgroundoperations.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.deepak.backgroundoperations.R
import com.deepak.backgroundoperations.model.PhoneTypesResponse
import com.deepak.backgroundoperations.viewModel.PhoneNameViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CoroutineDownload: Fragment(), FragmentUtilsBase {

    private val TAG: String = this.javaClass.name
    private val phoneNameViewModel by viewModels<PhoneNameViewModel>()

    override var showDialog = mutableStateOf(false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return ComposeView(requireContext()).apply {
            setContent {
                /*
                Surface is a basic building block for displaying content and can be used to wrap
                other composable to provide background color, elevation, padding and other layout props
                 */
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CoroutinePageElements()
                }
            }
        }
    }

    @Preview(name = "Light Mode", showSystemUi = true)
    @Composable
    fun CoroutinePageElements() {
        var isListViewVisible by remember {
            mutableStateOf(false)
        }

        InfoButton {
            showDialog.value = true
        }

        if (showDialog.value) {
            DisplayAlertDialog("Coroutines", stringResource(id = R.string.coroutine_explanation))
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
                    isListViewVisible = true
                    phoneNameViewModel.getPhoneTypeList()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Download Data")
            }
            Button(
                onClick = {
                    /*TODO*/
                    isListViewVisible = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Builders")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = isListViewVisible) {
                    Text(
                        text = "Lazy Column",
                        modifier = Modifier.padding(30.dp),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = TextUnit(value = 20f, type = TextUnitType.Sp)
                        )
                    )

                }
                // Listview to display items
                AnimatedVisibility(visible = isListViewVisible) {
                    ListOfPhoneNames(nameList = phoneNameViewModel.phoneNameListResponse)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Builders(isListViewVisible = false)
                }
            }
        }
    }

    @Composable
    private fun ListOfPhoneNames(nameList: List<PhoneTypesResponse>) {

        // lazy column for displaying listview
        LazyColumn {
            items(nameList) { typesOfPhones ->
                Text(text = typesOfPhones.name, modifier = Modifier.padding(15.dp))
                HorizontalDivider()
            }
        }
    }

    @Composable
    private fun Builders(isListViewVisible: Boolean) {
        AnimatedVisibility(
            visible = !isListViewVisible
        )
        {
            Text(
                text = stringResource(id = R.string.coroutine_builders),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Color.Black,
                fontSize = 16.sp
            )
        }
        AnimatedVisibility(
            visible = !isListViewVisible
        )
        {
            Text(
                text = stringResource(id = R.string.cor_builders_launch) + "\n RUNS IN: " + launch(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Color.Black,
                fontSize = 16.sp
            )
        }
        AnimatedVisibility(
            visible = !isListViewVisible
        )
        {
            Text(
                text = stringResource(id = R.string.cor_builders_async) + "\n RUNS IN: " + async(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Color.Black,
                fontSize = 16.sp
            )
        }
        AnimatedVisibility(
            visible = !isListViewVisible
        )
        {
            Text(
                text = stringResource(id = R.string.cor_builders_runblocking) + "\n RUNS IN: " + async(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Color.Black,
                fontSize = 16.sp
            )
        }
    }

    private fun launch(): String {
        var threadName = ""
        lifecycleScope.launch {
            threadName = Thread.currentThread().name
            Log.i("LAUNCH Runs in: ", threadName)
        }
        return threadName
    }

    private fun async(): String {
        var threadName = ""

        lifecycleScope.launch {
            val deferred = async {
                threadName = Thread.currentThread().name
                5
            }
            Log.d("Async: ", deferred.await().toString())
            Log.d("Async Runs in: ", threadName)
        }
        return threadName
    }
}