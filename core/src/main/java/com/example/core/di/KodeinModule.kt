package com.example.core.di

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.generic.on

class KodeinModule(application : Application) {

    val container = Kodein.lazy {
        modules.forEach {
            import(it)
        }
    }.on(application)

private val modules = listOf(
    commonModule(application),
    usecaseModule,
    apiModule
)
}