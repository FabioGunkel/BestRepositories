package com.example.model.feature.repositorysearch.remote

import com.example.model.feature.repositorysearch.model.RepositoryResponse
import io.reactivex.Single

class RepositoryApi(
    private val repositoryService : SearchRepositoryService
) {

    fun getSortedRepositories(queryMap : HashMap<String, String>): Single<RepositoryResponse>
            = repositoryService.getRepositoriesByStars(queryMap)
}