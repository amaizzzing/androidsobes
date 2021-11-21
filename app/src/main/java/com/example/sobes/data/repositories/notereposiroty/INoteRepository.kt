package com.example.sobes.data.repositories.notereposiroty

import com.example.sobes.data.entities.NotesEntity

interface INoteRepository {
    suspend fun insertNote(note: NotesEntity)

    suspend fun updateNote(name: String, text: String, lat: Double, lon: Double)

    suspend fun updateTextByLatLon(lat: Double, lon: Double, text: String)

    suspend fun getNoteByLatLon(lat: Double, lon: Double): NotesEntity?

    suspend fun getAllNotes(): List<NotesEntity>
}