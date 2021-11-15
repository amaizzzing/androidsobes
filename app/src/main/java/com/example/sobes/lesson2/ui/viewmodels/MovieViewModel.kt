package com.example.sobes.lesson2.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.sobes.lesson2.data.repositories.INetworkMovieRepository
import com.example.sobes.lesson2.ui.BaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val networkMovieRepository: INetworkMovieRepository
): BaseViewModel<BaseState>() {
    fun getMovieInfo(idMovie: Int) = viewModelScope.launch {
        setData(BaseState.Loading)

        val movieInfo = withContext(Dispatchers.IO) {
            networkMovieRepository.getMovieInfo(idMovie)
        }

        setData(BaseState.Success(movieInfo))
    }

    fun getActors(idMovie: Int) = viewModelScope.launch {
        setData(BaseState.Loading)

        val actors = withContext(Dispatchers.IO) {
            networkMovieRepository.getActorsForMovie(idMovie)
        }

        setData(BaseState.Success(actors))
    }
}