package com.example.sobes.ui.adapters.presenters

import com.example.sobes.data.entities.NotesEntity

class NotesAdapterPresenter: INotesAdapterPresenter {
    private var notesList = mutableListOf<NotesEntity>()

    override fun setList(list: List<NotesEntity>) {
        notesList.clear()
        notesList.addAll(list)
    }

    override fun getList(): List<NotesEntity> = notesList

    override var itemClickListener: ((NotesItemView) -> Unit)? = null

    override fun bindView(view: NotesItemView) {
        val note = notesList[view.pos]

        view.setTitle(note.name)
        view.setNoteText(note.text)
        view.setLat(note.lat)
        view.setLon(note.lon)
    }

    override fun getCount(): Int = notesList.size
}