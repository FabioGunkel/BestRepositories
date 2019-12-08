package com.example.model.feature.repositorysearch.remote

import com.example.model.feature.repositorysearch.model.RepositoryResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SearchRepositoryService {

    @GET("/search/repositories")
    fun getRepositoriesByStars(
        @QueryMap filter: Map<String, String>
    ):
            Single<RepositoryResponse>

}