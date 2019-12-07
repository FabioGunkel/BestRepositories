package com.example.viewmodel.feature

import androidx.lifecycle.MutableLiveData
import com.example.commons.util.ResourceProvider
import com.example.usecase.GetBestRepositoriesUseCase
import com.example.usecase.SearchRepositoriesRequest
import com.example.viewmodel.base.BaseViewModel
import com.example.commons.extension.applyLoading
import com.example.usecase.base.Result
import com.example.viewmodel.R
import com.example.viewmodel.base.ErrorUiModel
import com.example.viewmodel.base.toUiModel
import com.example.viewmodel.feature.model.RepositoryUIModel
import com.example.viewmodel.feature.model.toUIModel
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo

class HomeViewModel(
    private val resourceProvider : ResourceProvider,
    private val searchRepositoriesUseCase: GetBestRepositoriesUseCase,
    private val schedulerMain: Scheduler,
    private val schedulersIO : Scheduler
) : BaseViewModel() {
    private val _page = MutableLiveData<Int>().apply{
        value = 1
    }

    private val _repositories = MutableLiveData<ArrayList<RepositoryUIModel>>()

    init {
        startSearch()
    }

    private fun startSearch() {
        searchRepositoriesUseCase.execute(SearchRepositoriesRequest(_page.value!!))
            .applyLoading(mutableLoading)
            .subscribeOn(schedulersIO)
            .observeOn(schedulerMain)
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
}