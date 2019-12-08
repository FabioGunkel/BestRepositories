package com.example.bestrepositories.feature.home.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bestrepositories.databinding.ItemRepositoryBinding
import com.example.viewmodel.feature.home.model.RepositoryUIModel

class RepositoryViewHolder(val binding : ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

    public fun bind(repository : RepositoryUIModel) = with(binding){
        textviewRepositoryTitle.text = repository.repositoryName

        Glide.with(binding.root)
            .load(repository.ownerPictureUrl)
            .centerCrop()
            .into(imageviewOwnerAvatar)

        textviewDescription.text = repository.description

        textviewForksNumber.text = repository.forks

        textviewStarNumber.text = repository.stars

        textviewOwnerName.text = repository.ownerName
    }
}