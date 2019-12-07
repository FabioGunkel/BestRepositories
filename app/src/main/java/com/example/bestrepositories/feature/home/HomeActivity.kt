package com.example.bestrepositories.feature.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bestrepositories.BaseActivity
import com.example.bestrepositories.R
import com.example.bestrepositories.databinding.ActivityHomeBinding
import com.example.bestrepositories.extension.viewModel
import com.example.viewmodel.di.homeModule
import com.example.bestrepositories.util.ViewModelFactory
import com.example.viewmodel.feature.HomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

class HomeActivity : BaseActivity() {


    override val kodein = Kodein.lazy {
        extend(super.kodein)
        import(homeModule)
        bind<ViewModelProvider.Factory>() with provider { ViewModelFactory(kodein) }
    }

    private val homeViewModel: HomeViewModel by viewModel()


    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home).let {
            binding = it
            it.homeViewModel = homeViewModel
            it.lifecycleOwner = this
            it.executePendingBindings()
        }

    }
}
