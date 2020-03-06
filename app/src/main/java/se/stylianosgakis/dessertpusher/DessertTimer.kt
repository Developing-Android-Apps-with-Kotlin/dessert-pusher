package se.stylianosgakis.dessertpusher

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

class DessertTimer(
    lifecycle: Lifecycle
) : LifecycleObserver {
    private var secondsCount = 0
    private lateinit var job: Job
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    init {
        lifecycle.addObserver(this)
    }

    @ExperimentalTime
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTimer() {
        job = coroutineScope.launch {
            while (true) {
                delay(1.seconds.toLongMilliseconds())
                Timber.i("Timer is at : ${secondsCount++}")
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer() {
        job.cancel()
    }
}