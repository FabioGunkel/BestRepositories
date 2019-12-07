package com.example.viewmodel.feature.model

import com.example.usecase.RepositoryDomain

data class RepositoryUIModel(
    val repositoryName : String,
    val stars : String,
    val forks : String,
    val ownerName : String,
    val ownerPictureUrl : String
)

fun RepositoryDomain.toUIModel() : RepositoryUIModel {

    return RepositoryUIModel(
        repositoryName = repositoryName,
        stars = stars.toString(),
        forks = forks.toString(),
        ownerName = ownerName,
        ownerPictureUrl = ownerPictureUrl
    )
}