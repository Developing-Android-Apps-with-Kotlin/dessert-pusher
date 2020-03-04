package se.stylianosgakis.dessertpusher

import android.app.Application
import timber.log.Timber

class DessertPusherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}