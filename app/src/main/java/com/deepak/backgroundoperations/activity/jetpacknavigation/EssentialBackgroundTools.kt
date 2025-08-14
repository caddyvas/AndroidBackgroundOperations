package com.deepak.backgroundoperations.activity.jetpacknavigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.deepak.backgroundoperations.R

@Preview(name = "Light Mode", showSystemUi = true)
@Composable
fun EssentialToolsPreview() {
    // provide dummy implementations for preview
    val dummyNavController = rememberNavController()
    EssentialTools(
        onBack = { /* do nothing, dummy purpose } */ },
        navController = dummyNavController
    )
}

@Composable
fun EssentialTools(onBack: () -> Unit, navController: NavController) {
    BackHandler {
        onBack() // triggers navigation back to MainActivity screen
    }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.essential_tools_work_manager),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            color = Color.Black,
            textAlign = TextAlign.Justify,
            fontSize = 16.sp
        )
        IconClickFunction {
            navController.navigate(Routes.WORK_MANAGER_NAVIGATION_SCREEN)
        }

        Text(
            text = stringResource(id = R.string.essential_tools_job_scheduler),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            color = Color.Black,
            textAlign = TextAlign.Justify,
            fontSize = 16.sp
        )
        IconClickFunction {
            navController.navigate(Routes.JOB_SCHEDULER_NAVIGATION_SCREEN)
        }

        Text(
            text = stringResource(id = R.string.essential_tools_alarm_manager),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            color = Color.Black,
            textAlign = TextAlign.Justify,
            fontSize = 16.sp
        )
        IconClickFunction {
            navController.navigate(Routes.ALARM_MANAGER_NAVIGATION_SCREEN)
        }
    }
}

@Composable
private fun IconClickFunction(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(5.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            modifier = Modifier
                .size(35.dp),
            contentDescription = null,
            tint = colorResource(id = R.color.teal_200)
        )
    }
}