package com.example.sobes.lesson2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.sobes.databinding.ActorsItemBinding
import com.example.sobes.lesson2.data.entity.uimodels.ActorsItemModel
import com.example.sobes.lesson2.data.services.IImageLoader

class ActorsAdapter(
    private val imageLoader: IImageLoader<ImageView>,
    var actorsList: List<ActorsItemModel>
): RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: ActorsItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun setImage(image: String) {
            imageLoader.loadIntoWithRoundCorners(image, binding.actorImage, 16)
        }
        fun setName(name: String) {
            binding.actorName.text = name
        }
        fun setRole(role: String) {
            binding.actorRole.text = role
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ActorsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(actorsList[position]) {
            holder.setImage(image)
            holder.setName(name)
            holder.setRole(role)
        }

    }

    override fun getItemCount(): Int = actorsList.size
}