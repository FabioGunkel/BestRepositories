package com.example.bestrepositories

import android.app.Application
import com.example.core.di.KodeinModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class App : Application(), KodeinAware {
    override val kodein: Kodein = KodeinModule(this).container

}