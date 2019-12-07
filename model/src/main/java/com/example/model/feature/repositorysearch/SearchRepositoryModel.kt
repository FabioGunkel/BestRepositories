package com.example.model.feature.repositorysearch

import com.example.model.feature.repositorysearch.model.RepositoryResponse
import com.example.model.feature.repositorysearch.remote.RepositoryApi
import io.reactivex.Single

class SearchRepositoryModel(
    val repositoryApi: RepositoryApi
) {

    fun getRepositoriesByPopularity(page : Int) : Single<RepositoryResponse>{

        return repositoryApi.getSortedRepositories(STARS, page)
    }


    companion object{
        const val STARS = "stars"
    }

}