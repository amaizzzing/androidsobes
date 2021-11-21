package com.example.sobes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sobes.R
import com.example.sobes.databinding.NotesItemBinding
import com.example.sobes.ui.adapters.presenters.INotesAdapterPresenter
import com.example.sobes.ui.adapters.presenters.NotesItemView

class NotesAdapter(
    private val notesAdapterPresenter: INotesAdapterPresenter
): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: NotesItemBinding
    ): RecyclerView.ViewHolder(binding.root), NotesItemView {
        override fun setTitle(title: String) {
            binding.noteItemName.text = title
        }

        override fun setNoteText(text: String) {
            binding.noteItemDescription.text = text
        }

        override fun setLat(lat: Double) {
            binding.noteItemLat.text = binding.root.context.resources.getString(R.string.lat_dialog_text, lat)
        }

        override fun setLon(lon: Double) {
            binding.noteItemLon.text = binding.root.context.resources.getString(R.string.lon_dialog_text, lon)
        }

        override var pos = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            NotesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        notesAdapterPresenter.bindView(
            holder.apply {
                pos = position
                itemView.setOnClickListener {
                    notesAdapterPresenter.itemClickListener?.invoke(this)
                }
            }
        )

    override fun getItemCount() = notesAdapterPresenter.getCount()
}