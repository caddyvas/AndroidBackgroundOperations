package com.deepak.backgroundoperations

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

/**
 * Application class is serves as the base class for maintaining global application state within an
 * Android app. It is instantiated before any other application components such as activities, services,
 * or broadcast receivers, when the application process is created.
 */
class BGOApplication : Application() {

    companion object {
        val activityStack = mutableListOf<String>()

        // access this globally
        fun getFormattedStack(): String {
            return "Activity_Stack_Count: ${activityStack.size}\n${activityStack.joinToString(", ")}"
        }
    }

    // track activity stack here
    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                val name = activity.javaClass.simpleName
                if (!activityStack.contains(name)) {
                    activityStack.add(name)
                    logStack("Created: ", name)
                }
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(p0: Activity) {
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                val name = activity.javaClass.simpleName
                activityStack.remove(name)
                logStack("Destroyed: ", name)
            }

            private fun logStack(event: String, activityName: String) {
                Log.d("Activity_Tracker: ", "$event: $activityName")
                Log.d(
                    "Activity_Tracker: ",
                    "Current Stack: (${activityStack.size}): $activityStack"
                )
            }
        })
    }
}

