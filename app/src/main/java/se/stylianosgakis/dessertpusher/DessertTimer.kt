package se.stylianosgakis.dessertpusher

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class DessertTimer(
    lifecycle: Lifecycle
) : LifecycleObserver {
    private var secondsCount = 0
    private lateinit var job: Job
    private val coroutineScope = lifecycle.coroutineScope

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTimer() {
        job = coroutineScope.launch(Dispatchers.Default) {
            while (true) {
                delay(1_000)
                Timber.i("Timer is at : ${++secondsCount}")
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer() {
        job.cancel()
    }
}