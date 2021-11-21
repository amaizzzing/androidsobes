package com.example.sobes.lesson2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sobes.databinding.TopMovieItemBinding
import com.example.sobes.lesson2.data.entity.uimodels.MovieModel
import com.example.sobes.lesson2.data.services.IImageLoader

class TopMoviePagingAdapter(
    private val imageLoader: IImageLoader<ImageView>,
    private val clickListener: ((Int) -> Unit)? = null
): PagingDataAdapter<MovieModel, TopMoviePagingAdapter.TopMovieViewHolder>(TopMovieDiffCallBack()) {
        inner class TopMovieViewHolder(
            val binding: TopMovieItemBinding
        ) : RecyclerView.ViewHolder(binding.root){
            fun setTitle(title: String) {
                binding.nameTopMovie.text = title
            }

            fun setDate(date: String) {
                binding.dateTopMovie.text = date
            }

            fun setImage(image: String) {
                imageLoader.loadIntoWithRoundCorners(image, binding.imageTopMovie, 16)
            }
        }

    override fun onBindViewHolder(holder: TopMovieViewHolder, position: Int) {
        holder.setTitle(getItem(position)?.movieName ?: "")
        holder.setDate(getItem(position)?.movieDate ?: "")
        holder.setImage(getItem(position)?.image ?: "")
        holder.apply {
            itemView.setOnClickListener {
                clickListener?.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMovieViewHolder =
        TopMovieViewHolder(
            TopMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    fun getMovieItem(pos: Int): MovieModel? =
        getItem(pos)
}