package com.erdemkaya.scribbledash

import android.app.Application
import com.erdemkaya.scribbledash.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ScribbleDashApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ScribbleDashApp)
            androidLogger()
            modules(appModule)

        }
    }
}