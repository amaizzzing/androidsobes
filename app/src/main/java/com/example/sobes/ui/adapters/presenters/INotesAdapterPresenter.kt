package com.example.sobes.ui.adapters.presenters

import com.example.sobes.data.entities.NotesEntity

interface INotesAdapterPresenter: IListPresenter<NotesItemView> {
    fun setList(list: List<NotesEntity>)

    fun getList(): List<NotesEntity>
}