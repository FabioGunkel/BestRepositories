package com.example.model.feature.repositorysearch.remote

import com.example.model.feature.repositorysearch.model.RepositoryResponse
import io.reactivex.Single

class RepositoryApi(
    private val repositoryService : SearchRepositoryService
) {

    fun getSortedRepositories(sortBy : String, page : Int): Single<RepositoryResponse>
            = repositoryService.getRepositoriesByStars(sortBy, page)
}