package com.example.model.feature.repositorysearch.remote

import com.example.model.feature.repositorysearch.model.RepositoryResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRepositoryService {

    @GET("/search")
    fun getRepositoriesByStars(
        @Query("sort") sortBy: String,
        @Query("page") page: Int
    ):
            Single<RepositoryResponse>

}