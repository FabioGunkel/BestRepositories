package com.example.bestrepositories.feature.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.bestrepositories.BaseActivity
import com.example.bestrepositories.R
import com.example.bestrepositories.feature.home.di.homeModule
import com.example.bestrepositories.util.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

class HomeActivity : BaseActivity() {


    override val kodein = Kodein.lazy {
        extend(super.kodein)
        import(homeModule)
        bind<ViewModelProvider.Factory>() with provider { ViewModelFactory(kodein) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
