package com.example.viewmodel.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.commons.util.ResourceProvider
import com.example.usecase.GetBestRepositoriesUseCase
import com.example.usecase.SearchRepositoriesRequest
import com.example.viewmodel.base.BaseViewModel
import com.example.commons.extension.applyLoading
import com.example.commons.util.ScrollListener
import com.example.usecase.base.Result
import com.example.viewmodel.R
import com.example.viewmodel.base.toUiModel
import com.example.viewmodel.feature.home.model.RepositoryUIModel
import com.example.viewmodel.feature.home.model.toUIModel
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo

class HomeViewModel(
    private val resourceProvider : ResourceProvider,
    private val searchRepositoriesUseCase: GetBestRepositoriesUseCase,
    private val schedulerMain: Scheduler,
    private val schedulersIO : Scheduler
) : BaseViewModel(), ScrollListener {

    private var lockedLoading = false

    private val _page = MutableLiveData<Int>().apply{
        value = 1
    }

    private val _repositories = MutableLiveData<ArrayList<RepositoryUIModel>>()

    val repositories : LiveData<ArrayList<RepositoryUIModel>>
        get() = _repositories

    init {
        startSearch()
    }

    private fun startSearch() {
        searchRepositoriesUseCase.execute(SearchRepositoriesRequest(_page.value!!))
            .subscribeOn(schedulersIO)
            .observeOn(schedulerMain)
            .applyLoading(mutableLoading)
            .subscribe({
                when (it) {
                    is Result.Success -> {
                        _repositories.value =
                            ArrayList(it.data.rapositories.map { repositoryDomain ->
                                repositoryDomain.toUIModel()
                            })
                    }
                    is Result.Failure -> {
                        mutableError.value = it.error.toUiModel(
                            positiveText = resourceProvider.getString(R.string.ok),
                            onPositiveClick = {}
                        )
                    }
                }
            }, {

            }).addTo(compositeDisposable)
    }
    private fun continueSearch() {
        if(!checkAndLockLoading()) return
        _page.value = checkNotNull(_page.value) + 1
        searchRepositoriesUseCase.execute(SearchRepositoriesRequest(_page.value!!))
            .subscribeOn(schedulersIO)
            .observeOn(schedulerMain)
            .applyLoading(mutableLoading)
            .doAfterTerminate(::unlockLoading)
            .subscribe({
                when (it) {
                    is Result.Success -> {
                        _page.value = it.data.page
                        _repositories.value?.addAll(
                            ArrayList(it.data.rapositories.map { repositoryDomain ->
                                repositoryDomain.toUIModel()
                            }))

                        _repositories.value = _repositories.value
                    }
                    is Result.Failure -> {
                        _page.value = checkNotNull(_page.value) - 1
                        mutableError.value = it.error.toUiModel(
                            positiveText = resourceProvider.getString(R.string.ok),
                            onPositiveClick = {}
                        )
                    }
                }
            }, {

            })
            .addTo(compositeDisposable)
    }

    private fun checkAndLockLoading() : Boolean {
        return if(lockedLoading){
            false
        } else {
            lockedLoading = true
            true
        }

    }

    private fun unlockLoading(){
        lockedLoading = false
    }


    override fun scrolledToTheEnd() {
        loadMoreRepositories()
    }

    private fun loadMoreRepositories() {
        continueSearch()
    }

}