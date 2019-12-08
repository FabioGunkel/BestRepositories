package com.example.model.feature.repositorysearch

import com.example.model.feature.repositorysearch.local.RepositoryLocalDataSource
import com.example.model.feature.repositorysearch.model.RepositoryResponse
import com.example.model.feature.repositorysearch.remote.RepositoryApi
import io.reactivex.Observable
import io.reactivex.Single

class SearchRepositoryModel(
    private val repositoryApi: RepositoryApi,
    private val localDataSource: RepositoryLocalDataSource
) {

    fun getRepositoriesByPopularity(page : String) : Single<RepositoryResponse>{
        return Single.fromObservable(getLocalRepositoriesByPopularity(page).switchIfEmpty(getRemoteRepositoriesByPopularity(page)))
    }

    fun getRemoteRepositoriesByPopularity(page : String) : Observable<RepositoryResponse>{
        val map = HashMap<String, String>()
        map[PAGE] = page
        map[SORT] = STARS
        map[FILTER_PARAMETER] = LANGUAGE_KOTLIN

        return repositoryApi.getSortedRepositories(map).map {
            localDataSource.saveRepositoriesList(page.toInt(), it)
            it
        }.toObservable()
    }

    fun getLocalRepositoriesByPopularity(page : String) : Observable<RepositoryResponse> {
        return localDataSource.getRepositoriesFromPage(page.toInt())
    }


    companion object{
        const val PAGE = "page"
        const val SORT = "sort"
        const val STARS = "stars"
        const val FILTER_PARAMETER = "q"
        const val LANGUAGE_KOTLIN = "language:kotlin"
    }

}