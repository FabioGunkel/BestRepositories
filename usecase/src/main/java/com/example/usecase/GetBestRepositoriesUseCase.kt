package com.example.usecase

import com.example.model.feature.repositorysearch.SearchRepositoryModel
import com.example.model.feature.repositorysearch.model.Repository
import com.example.model.feature.repositorysearch.model.RepositoryResponse
import com.example.usecase.base.BaseUseCase
import com.example.usecase.base.Result
import com.example.usecase.base.toErrorResult
import com.example.usecase.base.toResult
import io.reactivex.Single

class GetBestRepositoriesUseCase(
    val searchRepositoryModel : SearchRepositoryModel
) : BaseUseCase<SearchRepositoriesRequest, RepositoriesResponseDomain>() {
    override fun execute(request: SearchRepositoriesRequest): Single<Result<RepositoriesResponseDomain>> {

        return searchRepositoryModel.getRepositoriesByPopularity(request.page)
            .map {
                it.toDomain(request.page)
            }.map {
                it.toResult()
            }
            .onErrorReturn {
            it.toErrorResult()
        }
    }

}


class RepositoriesResponseDomain(
    val page : Int,
    val rapositories : ArrayList<RepositoryDomain>
) : BaseUseCase.DomainResponse

class RepositoryDomain(
    val repositoryName : String,
    val stars : Int,
    val forks : Int,
    val ownerName : String,
    val ownerPictureUrl : String
)



