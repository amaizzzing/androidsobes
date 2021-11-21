package com.example.sobes.lesson2.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.sobes.lesson2.DEFAULT_LANGUAGE
import com.example.sobes.lesson2.IMAGE_URL
import com.example.sobes.lesson2.data.api.IFilmDatasource
import com.example.sobes.lesson2.data.api.MAX_ITEMS
import com.example.sobes.lesson2.data.api.SearchMoviesPagingSource
import com.example.sobes.lesson2.data.api.TopMoviesPagingSource
import com.example.sobes.lesson2.data.entity.uimodels.ActorsItemModel
import com.example.sobes.lesson2.data.entity.uimodels.MovieInfoModel
import com.example.sobes.lesson2.data.entity.uimodels.MovieModel
import com.example.sobes.toCorrectDate
import kotlinx.coroutines.flow.Flow

class NetworkMovieRepository(
    private val api: IFilmDatasource
): INetworkMovieRepository {
    override suspend fun getTopMovies(): Flow<PagingData<MovieModel>> =
        Pager(
            config = PagingConfig(
                pageSize = MAX_ITEMS,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TopMoviesPagingSource(api)
            }
        ).flow

    override suspend fun getMoviesByName(movieName: String): Flow<PagingData<MovieModel>> =
        Pager(
            config = PagingConfig(
                pageSize = MAX_ITEMS,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchMoviesPagingSource(api, movieName)
            }
        ).flow

    override suspend fun getMovieInfo(movieId: Int): MovieInfoModel{
        val info = api.getMovieInfo(movieId, DEFAULT_LANGUAGE)

        return MovieInfoModel(
            backdropImage = "$IMAGE_URL${info.backdrop_path}",
            posterImage = "$IMAGE_URL${info.poster_path}",
            title = info.title,
            released_date = info.release_date.toCorrectDate(),
            duration = info.runtime.toString(),
            genres = info.genres.joinToString(separator = ",") { it.name },
            description = info.overview.ifEmpty { "Нет описания" },
            actorList = listOf()
        )
    }

    override suspend fun getActorsForMovie(movieId: Int): List<ActorsItemModel> =
        api
            .getActorsForMovie(movieId, DEFAULT_LANGUAGE)
            .cast
            .map { cast ->
                ActorsItemModel(
                    image = "$IMAGE_URL${cast.profile_path}",
                    name = cast.name.ifEmpty { cast.original_name },
                    role = cast.character
                )
            }
}