package com.example.sobes.lesson2.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.sobes.lesson2.data.entity.uimodels.MovieModel

class TopMovieDiffCallBack : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.idMovie == newItem.idMovie
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }
}