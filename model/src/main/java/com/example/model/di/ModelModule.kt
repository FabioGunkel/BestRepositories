package com.example.model.di

import com.example.model.feature.repositorysearch.di.searchRepositoryModule
import org.kodein.di.Kodein


val modelModule = Kodein.Module("ModelModule"){

    import(networkModule)
    
    import(searchRepositoryModule)

}