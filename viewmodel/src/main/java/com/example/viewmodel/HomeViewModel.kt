package com.example.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.commons.util.ResourceProvider
import com.example.usecase.GetBestRepositoriesUseCase
import com.example.usecase.SearchRepositoriesRequest
import com.example.viewmodel.base.BaseViewModel
import com.example.bestrepositories.extension
import io.reactivex.Scheduler

class HomeViewModel(
    resourceProvider : ResourceProvider,
    searchRepositoriesUseCase: GetBestRepositoriesUseCase,
    schedulerMain: Scheduler,
    schedulersIO : Scheduler
) : BaseViewModel() {
    private val _page = MutableLiveData<Int>().apply{
        value = 1
    }


    init {
        searchRepositoriesUseCase.execute(SearchRepositoriesRequest(_page.value!!))
            .applyLoading(mutableLoading)
            .subscribeOn(schedulersIO)
            .observeOn(schedulerMain)
            .subscribe({
                when(it){

                }
            },{

            }).addTo(compositeDisposable)
    }
}