package com.example.core.di

import android.content.Context
import com.example.commons.util.ResourceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

const val SCHEDULERS_IO = "SCHEDULERS_IO"
const val SCHEDULERS_MAIN = "SCHEDULERS_MAIN"

fun commonModule(context: Context) = Kodein.Module("CommonModule")  {
    bind() from singleton { ResourceProvider(context) }
    bind(SCHEDULERS_IO) from singleton { Schedulers.io() }
    bind(SCHEDULERS_MAIN) from singleton { AndroidSchedulers.mainThread() }

}