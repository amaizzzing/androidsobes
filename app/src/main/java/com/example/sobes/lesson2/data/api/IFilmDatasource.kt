package com.example.sobes.lesson2.data.api

import com.example.sobes.BuildConfig
import com.example.sobes.lesson2.DEFAULT_LANGUAGE
import com.example.sobes.lesson2.data.entity.network.NetworkActorsEntity
import com.example.sobes.lesson2.data.entity.network.NetworkMovieInfoEntity
import com.example.sobes.lesson2.data.entity.network.NetworkRootMovieEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IFilmDatasource {
    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY}")
    suspend fun getTopMovies(
        @Query("language") language: String = DEFAULT_LANGUAGE,
        @Query("page") page: Int
    ): NetworkRootMovieEntity

    @GET("movie/{movieid}?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieInfo(
        @Path("movieid") movieid: Int,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): NetworkMovieInfoEntity

    @GET("movie/{movieid}/credits?api_key=${BuildConfig.API_KEY}")
    suspend fun getActorsForMovie(
        @Path("movieid") movieid: Int,
        @Query("language") language: String = DEFAULT_LANGUAGE
    ): NetworkActorsEntity

    @GET("search/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun getMoviesByName(
        @Query("language") language: String = DEFAULT_LANGUAGE,
        @Query("page") page: Int,
        @Query("query") query: String
    ): NetworkRootMovieEntity
}