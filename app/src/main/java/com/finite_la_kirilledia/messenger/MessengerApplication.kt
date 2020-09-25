package com.finite_la_kirilledia.messenger

import android.app.Application
import timber.log.Timber

class MessengerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}