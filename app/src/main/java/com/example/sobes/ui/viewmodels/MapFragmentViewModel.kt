package com.example.sobes.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.sobes.data.repositories.notereposiroty.INoteRepository
import com.example.sobes.ui.states.BaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapFragmentViewModel @Inject constructor(
    private val noteRepository: INoteRepository
): BaseViewModel<BaseState>() {
    fun getNotesData() = viewModelScope.launch {
        setData(BaseState.Loading)

        val list = withContext(Dispatchers.IO) {
            noteRepository.getAllNotes()
        }

        setData(BaseState.Success(list))
    }
}