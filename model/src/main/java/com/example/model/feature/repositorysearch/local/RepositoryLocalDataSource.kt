package com.example.model.feature.repositorysearch.local

import com.example.model.feature.repositorysearch.model.Repository
import com.example.model.feature.repositorysearch.model.RepositoryResponse
import io.reactivex.Observable
import io.reactivex.Single

class RepositoryLocalDataSource {

    val repositories by lazy{
        HashMap<Int, RepositoryResponse>()
    }

    fun saveRepositoriesList(page : Int, list : RepositoryResponse){
        repositories[page] = list
    }

    fun getRepositoriesFromPage(page : Int) : Observable<RepositoryResponse> {
        return repositories.get(page)?.let{ Observable.just(it) } ?: Observable.empty()
    }

}