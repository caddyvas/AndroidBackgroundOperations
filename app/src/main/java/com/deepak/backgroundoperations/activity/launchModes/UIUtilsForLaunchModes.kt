package com.deepak.backgroundoperations.activity.launchModes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.deepak.backgroundoperations.R

interface UIUtilsForLaunchModes {

    @Composable
    fun IconClickFunction(onClick: () -> Unit) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .padding(5.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                modifier = Modifier
                    .size(40.dp),
                contentDescription = null,
                tint = colorResource(id = R.color.teal_200)
            )
        }
    }
}