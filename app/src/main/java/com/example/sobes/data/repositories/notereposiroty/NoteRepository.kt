package com.example.sobes.data.repositories.notereposiroty

import com.example.sobes.data.entities.NotesEntity
import com.example.sobes.data.services.database.db.SobesDB

class NoteRepository(private val dbService: SobesDB): INoteRepository {
    override suspend fun insertNote(note: NotesEntity) {
        dbService.notesDao.insert(note)
    }

    override suspend fun updateNote(name: String, text: String, lat: Double, lon: Double) {
        dbService.notesDao.updateNote(name, text, lat, lon)
    }

    override suspend fun updateTextByLatLon(lat: Double, lon: Double, text: String) {
        dbService.notesDao.updateTextByLatLon(lat, lon, text)
    }

    override suspend fun getNoteByLatLon(lat: Double, lon: Double): NotesEntity? =
        dbService.notesDao.getByLatLon(lat, lon)

    override suspend fun getAllNotes(): List<NotesEntity> =
        dbService.notesDao.getAllNotes()
}