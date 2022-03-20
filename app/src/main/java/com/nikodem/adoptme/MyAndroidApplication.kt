package com.nikodem.adoptme

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.multidex.MultiDex
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.nikodem.adoptme.di.appModule
import com.nikodem.adoptme.di.networkModule
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyAndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
//            StrictMode.enableDefaults()
        }

        startKoin {
//            androidLogger()
            androidContext(this@MyAndroidApplication)
            modules(networkModule, appModule)
        }

        val coilOkHttpClient = get<OkHttpClient>().newBuilder()
            .cache(CoilUtils.createDefaultCache(applicationContext))
            .build()

        Coil.setImageLoader {
            ImageLoader.Builder(applicationContext)
                .okHttpClient(coilOkHttpClient)
                .build()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}