package com.deepak.backgroundoperations.fragments

import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.deepak.backgroundoperations.R

/*
Class that contains utilities to the fragments
 */
interface FragmentUtilsBase {

    var showDialog: MutableState<Boolean>

    @Composable
    fun DisplayAlertDialog(title: String, content: String) {
        AlertDialog(onDismissRequest = { showDialog.value = false },
            confirmButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("OK")
                }
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = content) // comes from strings.xml
            }
        )
    }

    @Composable
    fun InfoButton(onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(5.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(45.dp)
                        .padding(2.dp),
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Info",
                    tint = colorResource(id = R.color.teal_200)
                )
            }
        }
    }
}