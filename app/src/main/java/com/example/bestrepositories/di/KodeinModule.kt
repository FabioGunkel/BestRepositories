package com.example.bestrepositories.di

import android.app.Application
import com.example.commons.di.commonModule
import com.example.viewmodel.di.viewModelModule
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
    viewModelModule
)
}