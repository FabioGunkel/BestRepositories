package com.example.model.feature.repositorysearch.di

import com.example.model.di.DITags
import com.example.model.feature.repositorysearch.SearchRepositoryModel
import com.example.model.feature.repositorysearch.local.RepositoryLocalDataSource
import com.example.model.feature.repositorysearch.remote.RepositoryApi
import com.example.model.feature.repositorysearch.remote.SearchRepositoryService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

val searchRepositoryModule = Kodein.Module("SearchRepository") {

    bind() from singleton {
        SearchRepositoryModel(
            instance(),
            instance()
        )
    }

    bind<SearchRepositoryService>() with singleton {
        instance<Retrofit>(DITags.RETROFIT).create(
            SearchRepositoryService::class.java
        )
    }

    bind<RepositoryApi>() with singleton { RepositoryApi(instance()) }
    bind<RepositoryLocalDataSource>() with singleton { RepositoryLocalDataSource() }
}