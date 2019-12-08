package com.example.bestrepositories.feature.home

import android.os.Bundle
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bestrepositories.BaseActivity
import com.example.bestrepositories.R
import com.example.bestrepositories.databinding.ActivityHomeBinding
import com.example.bestrepositories.extension.viewModel
import com.example.bestrepositories.feature.home.adapter.RepositoryAdapter
import com.example.bestrepositories.util.ViewModelFactory
import com.example.viewmodel.feature.home.HomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

class HomeActivity : BaseActivity() {


    override val kodein = Kodein.lazy {
        extend(super.kodein)
        bind<ViewModelProvider.Factory>() with provider { ViewModelFactory(kodein) }
    }

    private val homeViewModel: HomeViewModel by viewModel()


    private lateinit var binding: ActivityHomeBinding

    private val repositoriesAdapter by lazy { RepositoryAdapter(arrayListOf(), homeViewModel)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home).let {
            binding = it
            it.homeViewModel = homeViewModel
            it.lifecycleOwner = this
            it.executePendingBindings()
        }

        setupBaseViewModels(homeViewModel)

    }

    override fun onStart() {
        super.onStart()

        setupRecyclerView()


        homeViewModel.repositories.observe(this, Observer {
            repositoriesAdapter.add(it)
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerviewRepositories.adapter = repositoriesAdapter
        binding.recyclerviewRepositories.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}
