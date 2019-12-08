package com.example.model

import com.example.model.feature.repositorysearch.SearchRepositoryModel
import com.example.model.feature.repositorysearch.local.RepositoryLocalDataSource
import com.example.model.feature.repositorysearch.model.RepositoryResponse
import com.example.model.feature.repositorysearch.remote.RepositoryApi
import com.example.model.feature.repositorysearch.remote.SearchRepositoryService
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
class ModelUnitTests {

    @Mock
    private lateinit var repositoryLocalDataSource: RepositoryLocalDataSource

    @Mock
    private lateinit var searchRepositoryService: RepositoryApi

    lateinit var model: SearchRepositoryModel

    lateinit var cachedResponse : RepositoryResponse

    @Before
    fun before() {
        model = SearchRepositoryModel(searchRepositoryService, repositoryLocalDataSource)
    }

    @Test
    fun `Should do a sucessfull recover a page and cache it`() {
        val page = 1

        Mockito.`when`(repositoryLocalDataSource.saveRepositoriesList(1, getMockedResponse())).doAnswer {
            cachedResponse = (it.getArgument(1) as RepositoryResponse)
            return@doAnswer Unit
        }

        Mockito.`when`(searchRepositoryService.getSortedRepositories(any())).thenReturn(
            Single.just(getMockedResponse())
        )


        val response = model.getRemoteRepositoriesByPopularity(page.toString()).blockingLast()

        val mockedResponse = getMockedResponse()

        assert(
            response.totalCount == mockedResponse!!.totalCount &&
                   response.incompleteResults == mockedResponse.incompleteResults &&
                    response.repositories.size == mockedResponse.repositories.size
        )

    }

    @Test
    fun `Should fail recovering a cached page and then request the remote one`() {
        val page = 1


        Mockito.`when`(repositoryLocalDataSource.getRepositoriesFromPage(1)).thenReturn(
            Observable.empty()
        )

        Mockito.`when`(repositoryLocalDataSource.saveRepositoriesList(1, getMockedResponse())).doAnswer {
            cachedResponse = (it.getArgument(1) as RepositoryResponse)
            return@doAnswer Unit
        }



        Mockito.`when`(searchRepositoryService.getSortedRepositories(any())).thenReturn(
            Single.just(getMockedResponse())
        )


        val response = model.getRepositoriesByPopularity(page.toString()).blockingGet()

        val mockedResponse = getMockedResponse()

        assert(
            response.totalCount == mockedResponse!!.totalCount &&
                   response.incompleteResults == mockedResponse.incompleteResults &&
                    response.repositories.size == mockedResponse.repositories.size
        )

    }

    private fun getMockedResponse() = RepositoryResponse(false, arrayListOf(), Int.MAX_VALUE)

    private fun getQueryMap(page: Int): HashMap<String, String> {
        val map = HashMap<String, String>()
        map["PAGE"] = page.toString()
        map["sort"] = "stars"
        map["q"] = "language:kotlin"
        return map
    }
}
