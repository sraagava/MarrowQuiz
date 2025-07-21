package com.raagava.android.interview.apps.marrowquiz

import android.app.Application
import com.raagava.android.interview.apps.marrowquiz.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarrowQuiz : Application() {

    override fun onCreate() {
        super.onCreate()

        setupDI()
    }

    private fun setupDI() {
        startKoin {
            androidContext(this@MarrowQuiz)
            modules(koinModules)
        }
    }

}