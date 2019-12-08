package com.example.commons.extension

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Single

fun <T> Single<T>.applyLoading(loading: MutableLiveData<Boolean>): Single<T> =
    doOnSubscribe { loading.value = true }
        .doFinally { loading.value = false }

