package se.stylianosgakis.dessertpusher

import android.os.Handler
import timber.log.Timber

class DessertTimer {
    private var secondsCount = 0
    private var handler = Handler()
    private lateinit var runnable: Runnable

    fun startTimer() {
        runnable = Runnable {
            secondsCount++
            Timber.i("Timer is at : $secondsCount")
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    fun stopTimer() {
        handler.removeCallbacks(runnable)
    }
}