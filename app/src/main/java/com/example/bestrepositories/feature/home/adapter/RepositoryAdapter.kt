package com.example.bestrepositories.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bestrepositories.R
import com.example.bestrepositories.feature.home.viewholder.RepositoryViewHolder
import com.example.commons.util.ScrollListener
import com.example.viewmodel.feature.home.model.RepositoryUIModel

class RepositoryAdapter(val repositoriesList: ArrayList<RepositoryUIModel> = arrayListOf(), val listener : ScrollListener) :
    RecyclerView.Adapter<RepositoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepositoryViewHolder(
            DataBindingUtil.inflate(inflater, R.layout.item_repository, parent, false)
        )
    }


    override fun getItemCount(): Int {
        return repositoriesList.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repository = repositoriesList[position])
        if(position+1 == repositoriesList.size){
            listener.scrolledToTheEnd()
        }
    }

    fun restartList(newList: ArrayList<RepositoryUIModel>) {
        repositoriesList.clear()
        repositoriesList.addAll(newList)
        notifyDataSetChanged()
    }

    fun add(newList: ArrayList<RepositoryUIModel>) {
        val oldSize = repositoriesList.size
        repositoriesList.clear()
        repositoriesList.addAll(newList)
        notifyItemInserted(oldSize)
    }

}