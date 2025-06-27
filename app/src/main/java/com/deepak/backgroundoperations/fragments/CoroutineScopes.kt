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
import com.deepak.backgroundoperations.R

class CoroutineScopes : Fragment(), FragmentUtilsBase {

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
                    ScopeComponents()
                }
            }
        }
    }

    @Preview(name = "Light Mode", showSystemUi = true)
    @Composable
    private fun ScopeComponents() {
        var textToDisplay by remember {
            mutableStateOf("")
        }

        InfoButton {
            showDialog.value = true
        }

        if (showDialog.value) {
            DisplayAlertDialog("Scopes", stringResource(id = R.string.coroutine_scope_explanation))
        }

        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Button(
                onClick = {
                    textToDisplay = context.getString(R.string.cor_scope_global)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "GlobalScope")
            }
            Button(
                onClick = {
                    textToDisplay = context.getString(R.string.cor_scope_coroutine)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "CoroutineScope")
            }
            Button(
                onClick = {
                    textToDisplay = context.getString(R.string.cor_scope_lifecycle)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "LifecycleScope")
            }
            Button(
                onClick = {
                    textToDisplay = context.getString(R.string.cor_scope_viewmodel)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "ViewModelScope")
            }
            Text(
                text = textToDisplay,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()),
                color = Color.Black,
                style = MaterialTheme.typography.displaySmall,
                fontSize = 16.sp
            )
        }
    }
}