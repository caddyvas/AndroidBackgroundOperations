package com.deepak.backgroundoperations.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.deepak.backgroundoperations.R
import com.deepak.backgroundoperations.androidService.BackgroundService
import com.deepak.backgroundoperations.androidService.ForegroundService
import com.deepak.backgroundoperations.networkServices.PhoneTypesAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class BackgroundActivity : AppCompatActivity(), OnClickListener {

    private var isThreadStart = false
    private lateinit var countTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private var activityType by Delegates.notNull<Int>()

    private var handler: Handler = Handler(Looper.getMainLooper())

    private val TAG: String = "BackgroundActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.background_activity)
        countTextView = findViewById(R.id.textView2)
        titleTextView = findViewById(R.id.textView1)
        descriptionTextView = findViewById(R.id.textView3)
        buttonOne = findViewById(R.id.button1)
        buttonOne.setOnClickListener(this)
        buttonTwo = findViewById(R.id.button2)
        buttonTwo.setOnClickListener(this)
        activityType = intent.extras?.getInt("activityType") ?: 0
        applyRequiredOperation(activityType)

    }

    /**
     * Based on the user's selection in the previous screen, apply whether it is a
     * Service, Coroutine, Handler or any other operation
     */
    private fun applyRequiredOperation(activityType: Int) {
        when (activityType) {
            MainActivity.BACKGROUND_SERVICE -> {
                titleTextView.text = resources.getText(R.string.background_service_name)
                buttonOne.text = resources.getText(R.string.start_service)
                buttonTwo.text = resources.getText(R.string.stop_service)
                descriptionTextView.text =
                    resources.getText(R.string.background_service_explanation)
            }

            MainActivity.FOREGROUND_SERVICE -> {
                titleTextView.text = resources.getText(R.string.foreground_service_name)
                buttonOne.text = resources.getText(R.string.start_service)
                buttonTwo.text = resources.getText(R.string.stop_service)
                descriptionTextView.text =
                    resources.getText(R.string.foreground_service_explanation)
            }

            MainActivity.HANDLER -> {
                titleTextView.text = resources.getText(R.string.handler_name)
                descriptionTextView.text = resources.getText(R.string.handler_explanation)
            }

            MainActivity.COROUTINE -> {
                titleTextView.text = resources.getText(R.string.coroutine_name)
                buttonOne.text = resources.getText(R.string.download_start)
                buttonTwo.text = resources.getText(R.string.download_stop)
                descriptionTextView.text = resources.getText(R.string.coroutine_explanation)
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button1 -> {
                when (activityType) {
                    MainActivity.BACKGROUND_SERVICE -> {
                        startService(Intent(this@BackgroundActivity, BackgroundService::class.java))
                    }

                    MainActivity.FOREGROUND_SERVICE -> {
                        startService(Intent(this@BackgroundActivity, ForegroundService::class.java))
                    }

                    MainActivity.HANDLER -> {
                        isThreadStart = true
                        threadFunctionUsingHandler()
                    }

                    MainActivity.COROUTINE -> {
                        //backgroundOperationUsingCoroutine()
                        lifecycleScope.launch(Dispatchers.IO) {
                           val response = PhoneTypesAPI().getPhoneTypes()
                           if(response.isSuccessful) {
                               Log.i(TAG, "Coroutine scope successful")
                               for(phoneType in response.body()!!) {
                                   Log.i(TAG, phoneType.name)
                               }
                           }else{
                               Log.i(TAG, "Coroutine scope Failed")
                           }
                       }
                    }
                }
            }

            R.id.button2 -> {
                when (activityType) {
                    MainActivity.BACKGROUND_SERVICE -> {
                        Intent(this, BackgroundService::class.java).also {
                            stopService(it)
                            Log.i(TAG, "Background Service Stopped in : ${Thread.currentThread().name}")
                            // TODO add philip lackner
                        }
                    }

                    MainActivity.FOREGROUND_SERVICE -> {
                        Intent(this, ForegroundService::class.java).also {
                            stopService(it)
                            Log.i(TAG, "Foreground Service Stopped in : ${Thread.currentThread().name}")
                            // TODO add philip lackner
                        }
                    }

                    MainActivity.HANDLER -> {
                        isThreadStart = false
                    }

                    MainActivity.COROUTINE -> {

                    }
                }
            }
        }
    }

    private fun threadFunctionUsingHandler() {
        Thread(Runnable() {
            var count: Int = 0
            while (isThreadStart) {
                try {
                    Thread.sleep(1000)
                    count++
                } catch (e: InterruptedException) {
                    Log.i(TAG, e.message.toString())
                }
                Log.i(
                    TAG,
                    "Thread ID in While Loop: ${Thread.currentThread().id}" +
                            "--- Count: $count"
                )
                // update textView (exception created intentionally
                // Only the original thread that created a view hierarchy can touch its views
                //countTextView.text = "$count"
                handler.post {
                    countTextView.text = "$count"
                }

                // you can also delay UI updates
                handler.postDelayed({
                    Log.i(
                        TAG, "Delayed Response after 1 second"
                    )
                }, 1000)
            }
        }).start()
    }

    private fun backgroundOperationUsingCoroutine() {
        lifecycleScope.launch(Dispatchers.IO) {
            Log.i(TAG, "Starting coroutine in Thread: ${Thread.currentThread().name}")
            val response = doNetworkCall()
            withContext(Dispatchers.Main) {
                Log.i(TAG, "Inside coroutine context in Thread: ${Thread.currentThread().name}")
                // update the text View with the result
                countTextView.text = response
            }
        }
    }

    private suspend fun doNetworkCall(): String {
        // assume there is a rest service call
        delay(3000L)
        return "{Success: 200}"

    }
}

/*setContent {

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (activityType) {
            MainActivity.SERVICE -> {
                DisplayServiceLayout()
            }

            MainActivity.HANDLER -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    DisplayHandlerLayout()
                }
            }

            MainActivity.COROUTINE -> {

            }
        }
    }
}
}

@Composable
fun DisplayServiceLayout() {

}

/**
* Composable functions need to have a MutableState in order to be programmatically updated.
* It can be created from an observable (like LiveData) by calling mutableStateOf()
*/
@Composable
fun DisplayHandlerLayout() {
//CircularProgressBar(percentage = 1.0f, number = 100)
Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(30.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top

) {
    Text(
        textAlign = TextAlign.Center,
        text = "HANDLER ACTIVITY",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(10.dp)
    )
    Button(
        onClick = {
            isThreadStart = true
            threadFunction()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "START THREAD")
    }
    Button(
        onClick = { isThreadStart = false },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "STOP THREAD")
    }

    //val myText by textToBeUpdated
    Text(
        textAlign = TextAlign.Center,
        text = textToBeUpdated,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(30.dp)
    )
}

}

@Composable
fun CircularProgressBar(
percentage: Float,
number: Int,
fontSize: TextUnit = 28.sp,
radius: Dp = 50.dp,
color: Color = Color.Green,
strokeWidth: Dp = 8.dp,
animDuration: Int = 1000,
animDelay: Int = 0
) {
var animationPlayed by remember {
    mutableStateOf(false)
}
val curPercentage = animateFloatAsState(
    targetValue = if (animationPlayed) percentage else 0f,
    animationSpec = tween(durationMillis = animDuration, delayMillis = animDelay),
    label = ""
)
LaunchedEffect(key1 = true) {
    animationPlayed = true
}
Box(contentAlignment = Alignment.Center, modifier = Modifier.size((radius * 2f))) {
    // draw your own shape
    Canvas(modifier = Modifier.size(radius * 2f)) {
        drawArc(
            color = color, -90f, 360 * curPercentage.value,
            useCenter = false, style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}
// number to load in progress
Text(
    text = (curPercentage.value * number).toInt().toString(),
    color = Color.Black,
    fontSize = fontSize,
    fontWeight = FontWeight.Bold
)
}

@Preview
@Composable
fun PreviewOfDisplayHandlerLayout() {
Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background
) {
    DisplayHandlerLayout()
}

}

@Preview
@Composable
fun PreviewOfCircularProgressBar() {
CircularProgressBar(percentage = 4.2f, number = 100)
}*/