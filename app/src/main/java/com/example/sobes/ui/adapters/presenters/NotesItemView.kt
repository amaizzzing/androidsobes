package com.example.sobes.ui.adapters.presenters

interface NotesItemView: IITemView {
    fun setTitle(title: String)
    fun setNoteText(text: String)
    fun setLat(lat: Double)
    fun setLon(lon: Double)
}