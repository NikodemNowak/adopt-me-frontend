package com.nikodem.adoptme.di

import com.nikodem.adoptme.services.AdoptMeApiService
import com.nikodem.adoptme.utils.ErrorHandlingCallAdapterFactory
import com.nikodem.adoptme.utils.NetworkHandler
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {
        NetworkHandler()
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return@single OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("http://192.168.50.249:8080/api/")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(ErrorHandlingCallAdapterFactory())
            .client(get())
            .build()
    }

    single<AdoptMeApiService> {
        get<Retrofit>().create(AdoptMeApiService::class.java)
    }
}