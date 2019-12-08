package com.example.viewmodel.di

import com.example.commons.di.SCHEDULERS_IO
import com.example.commons.di.SCHEDULERS_MAIN
import com.example.usecase.di.usecaseModule
import com.example.viewmodel.feature.home.HomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val viewModelModule = Kodein.Module("ViewModelModule") {

    import(usecaseModule)


    bind<HomeViewModel>() with provider {
        HomeViewModel(
            instance(),
            instance(),
            instance(SCHEDULERS_MAIN),
            instance(SCHEDULERS_IO)
        )
    }
}