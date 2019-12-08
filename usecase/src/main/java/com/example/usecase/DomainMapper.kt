package com.example.usecase

import com.example.model.feature.repositorysearch.model.Repository
import com.example.model.feature.repositorysearch.model.RepositoryResponse
import com.example.usecase.base.BaseUseCase


class SearchRepositoriesRequest(
    val page : Int
) : BaseUseCase.DomainRequest

fun RepositoryResponse.toDomain(page : Int) : RepositoriesResponseDomain {
    val repositories = ArrayList(this.repositories.map {
        it.toDomain()
    })

    return RepositoriesResponseDomain(page, repositories)
}

fun Repository.toDomain() = RepositoryDomain(
    repositoryName = name,
    stars = stargazersCount,
    forks = forks,
    ownerName = owner.login,
    ownerPictureUrl = owner.avatarUrl,
    description = description
)