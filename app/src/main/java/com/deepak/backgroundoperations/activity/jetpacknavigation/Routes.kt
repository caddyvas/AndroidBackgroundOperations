package com.deepak.backgroundoperations.activity.jetpacknavigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.deepak.backgroundoperations.activity.jetpacknavigation.alarmmanager.AlarmManagerNavigation
import com.deepak.backgroundoperations.activity.jetpacknavigation.jobscheduler.JobSchedulerNavigation
import com.deepak.backgroundoperations.activity.jetpacknavigation.workmanager.WorkManagerNavigation

object Routes {
    const val ESSENTIAL_BACKGROUND_TOOLS_SCREEN = "ESSENTIAL_BACKGROUND_TOOLS_SCREEN"
    const val WORK_MANAGER_NAVIGATION_SCREEN = "WORK_MANAGER_NAVIGATION_SCREEN"
    const val JOB_SCHEDULER_NAVIGATION_SCREEN = "JOB_SCHEDULER_NAVIGATION_SCREEN"
    const val ALARM_MANAGER_NAVIGATION_SCREEN = "ALARM_MANAGER_NAVIGATION_SCREEN"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHostGraph(
    context: Context,
    navHostController: NavHostController,
    initiateNavigationControllerState: MutableState<Boolean>
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.ESSENTIAL_BACKGROUND_TOOLS_SCREEN,
        builder = {
            composable(Routes.ESSENTIAL_BACKGROUND_TOOLS_SCREEN) {
                EssentialTools(onBack = {
                    initiateNavigationControllerState.value = false
                }, navHostController)
            }
            composable(Routes.WORK_MANAGER_NAVIGATION_SCREEN) {
                WorkManagerNavigation(context = context).InitiateWorkManagerNavigation()
            }
            composable(Routes.JOB_SCHEDULER_NAVIGATION_SCREEN) {
                JobSchedulerNavigation()
            }
            composable(Routes.ALARM_MANAGER_NAVIGATION_SCREEN) {
                AlarmManagerNavigation(context = context).InitiateAlarmManagerNavigation()
            }
        })
}
