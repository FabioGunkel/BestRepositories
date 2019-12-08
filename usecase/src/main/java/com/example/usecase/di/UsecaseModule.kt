package com.example.usecase.di

import com.example.model.di.modelModule
import com.example.usecase.GetBestRepositoriesUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val usecaseModule = Kodein.Module("UseCaseModule"){

    import(modelModule)

    bind<GetBestRepositoriesUseCase>() with singleton {
        GetBestRepositoriesUseCase(instance())
    }
}