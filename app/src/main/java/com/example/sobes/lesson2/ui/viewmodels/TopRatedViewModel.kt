package com.example.sobes.lesson2.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.sobes.lesson2.data.repositories.INetworkMovieRepository
import com.example.sobes.lesson2.ui.BaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopRatedViewModel @Inject constructor(
    private val networkMovieRepository: INetworkMovieRepository
): BaseViewModel<BaseState>() {
    fun getTopMovies() = viewModelScope.launch {
        setData(BaseState.Loading)

        networkMovieRepository
            .getTopMovies()
            .flowOn(Dispatchers.IO)
            .collectLatest { movies ->
                withContext(viewModelScope.coroutineContext) {
                    setData(BaseState.Success(movies))
                }
            }
    }

    fun getMoviesByName(name: String) = viewModelScope.launch {
        setData(BaseState.Loading)

        networkMovieRepository
            .getMoviesByName(name)
            .flowOn(Dispatchers.IO)
            .collectLatest { movies ->
                withContext(viewModelScope.coroutineContext) {
                    setData(BaseState.Success(movies))
                }
            }
    }
}