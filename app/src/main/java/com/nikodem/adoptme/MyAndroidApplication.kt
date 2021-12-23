package com.nikodem.adoptme

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.databinding.ktx.BuildConfig
import androidx.multidex.MultiDex
import com.nikodem.adoptme.di.appModule
import com.nikodem.adoptme.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyAndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            StrictMode.enableDefaults()
        }

        startKoin {
            androidLogger()
            androidContext(this@MyAndroidApplication)
            modules(networkModule, appModule)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}