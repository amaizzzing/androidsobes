package com.example.sobes.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.sobes.data.entities.NotesEntity
import com.example.sobes.data.repositories.notereposiroty.INoteRepository
import com.example.sobes.ui.states.BaseState
import com.example.sobes.utils.getCorrectLatLon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddMarkerDialogViewModel @Inject constructor(
    private val noteRepository: INoteRepository
): BaseViewModel<BaseState>() {
    fun getDialogData(lat: Double, lon: Double) = viewModelScope.launch {
        setData(BaseState.Loading)

        val note = withContext(Dispatchers.IO) {
            noteRepository.getNoteByLatLon(lat.getCorrectLatLon(), lon.getCorrectLatLon())
        }

        withContext(Dispatchers.Main) {
            setData(BaseState.Success(note))
        }
    }

    suspend fun getNoteByLatLon(lat: Double, lon: Double): NotesEntity? =
        noteRepository.getNoteByLatLon(lat, lon)

    suspend fun insertNote(lat: Double, lon: Double, name: String, text: String) {
        noteRepository.insertNote(
            NotesEntity(
                lat = lat.getCorrectLatLon(),
                lon = lon.getCorrectLatLon(),
                name = name,
                text = text
            )
        )
    }

    suspend fun updateNote(lat: Double, lon: Double, name: String, text: String) {
        noteRepository.updateNote(
            name = name,
            text = text,
            lat = lat.getCorrectLatLon(),
            lon = lon.getCorrectLatLon(),
        )
    }
}