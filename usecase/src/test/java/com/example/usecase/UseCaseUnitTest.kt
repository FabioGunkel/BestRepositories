package com.example.usecase

import com.example.model.feature.repositorysearch.SearchRepositoryModel
import com.example.model.feature.repositorysearch.local.RepositoryLocalDataSource
import com.example.model.feature.repositorysearch.model.Owner
import com.example.model.feature.repositorysearch.model.Repository
import com.example.model.feature.repositorysearch.model.RepositoryResponse
import com.example.model.feature.repositorysearch.remote.RepositoryApi
import com.example.model.feature.repositorysearch.remote.SearchRepositoryService
import com.example.usecase.base.Result
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doAnswer
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.HashMap

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class UseCaseUnitTest {

    lateinit var useCase: GetBestRepositoriesUseCase

    @Mock
    private lateinit var searchRepositoryModel: SearchRepositoryModel

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var owner: Owner



    @Before
    fun before() {
        useCase = GetBestRepositoriesUseCase(searchRepositoryModel)
    }

    @Test
    fun `Should do a sucessfull page recovery`() {
        val page = 1

        Mockito.`when`(repository.owner).thenReturn(
            owner
        )

        Mockito.`when`(repository.stargazersCount).thenReturn(
            10
        )
        Mockito.`when`(repository.forks).thenReturn(
            5
        )
        Mockito.`when`(repository.name).thenReturn(
            "Mock"
        )
        Mockito.`when`(repository.description).thenReturn(
            "Mock Description"
        )
        Mockito.`when`(owner.login).thenReturn(
            "Mock Login"
        )
        Mockito.`when`(owner.avatarUrl).thenReturn(
            "Mock Url"
        )


        Mockito.`when`(searchRepositoryModel.getRepositoriesByPopularity(page.toString())).thenReturn(
            Single.just(getMockedResponse())
        )



        val response = useCase.execute(SearchRepositoriesRequest(1)).blockingGet()

        assert(response is Result.Success)

        assert((response as Result.Success).data.page == 1)
        assert(response.data.rapositories.isNotEmpty())
        assert(response.data.rapositories[0].forks == 5)

    }

    private fun getMockedResponse() = RepositoryResponse(false, arrayListOf(getMockRepository()), Int.MAX_VALUE)

    private fun getMockRepository(): Repository {
        return repository
    }

}
