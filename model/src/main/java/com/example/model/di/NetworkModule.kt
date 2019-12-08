package com.example.model.di

import com.example.model.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = Kodein.Module("NetworkModule") {

    bind<Retrofit>(DITags.RETROFIT) with singleton {
        Retrofit.Builder()
            .baseUrl(BuildConfig.GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(instance(DITags.OKHTTP))
            .build()
    }

    bind<OkHttpClient>(DITags.OKHTTP) with singleton {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

}