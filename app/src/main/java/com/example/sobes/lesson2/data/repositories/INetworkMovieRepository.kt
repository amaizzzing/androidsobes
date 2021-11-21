package com.example.sobes.lesson2.data.repositories

import androidx.paging.PagingData
import com.example.sobes.lesson2.data.entity.uimodels.ActorsItemModel
import com.example.sobes.lesson2.data.entity.uimodels.MovieInfoModel
import com.example.sobes.lesson2.data.entity.uimodels.MovieModel
import kotlinx.coroutines.flow.Flow

interface INetworkMovieRepository {
    suspend fun getTopMovies(): Flow<PagingData<MovieModel>>

    suspend fun getMoviesByName(movieName: String): Flow<PagingData<MovieModel>>

    suspend fun getMovieInfo(movieId: Int): MovieInfoModel

    suspend fun getActorsForMovie(movieId: Int): List<ActorsItemModel>
}