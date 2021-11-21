package com.example.sobes.lesson2.data.api

import com.example.sobes.lesson2.data.entity.uimodels.MovieModel

class TopMoviesPagingSource(
    private val api: IFilmDatasource
): BasePagingSource<MovieModel>() {
    override suspend fun getData(pageIndex: Int, params: LoadParams<Int>): LoadResult.Page<Int, MovieModel> {
        val response = api.getTopMovies(
            page = pageIndex,
        )
        val movies = response.results ?: listOf()
        val nextKey = if (movies.isEmpty()) {
            null
        } else {
            pageIndex + (params.loadSize / MAX_ITEMS)
        }

        return LoadResult.Page(
            data = movies.map { MovieModel(it) },
            prevKey = if (pageIndex == FIRST_INDEX_ITEM) null else pageIndex,
            nextKey = nextKey
        )
    }
}