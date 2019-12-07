package com.example.bestrepositories.feature.home.di

import com.example.core.di.SCHEDULERS_IO
import com.example.core.di.SCHEDULERS_MAIN
import com.example.viewmodel.HomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val homeModule = Kodein.Module("HomeModule") {

    bind<HomeViewModel>() with provider {
        HomeViewModel(
            instance(),
            instance(),
            instance(SCHEDULERS_MAIN),
            instance(SCHEDULERS_IO)
        )
    }
}