package com.example.sobes.lesson2.data.entity.network

import com.google.gson.annotations.Expose

data class NetworkMovieInfoEntity (
    @Expose var adult: Boolean = false,
    @Expose var backdrop_path: String ="",
    @Expose var budget: Int = 0,
    @Expose var genres: List<NetworkMovieGenre> = listOf(),
    @Expose var homepage: String = "",
    @Expose var id: Int = 0,
    @Expose var imdb_id: String = "",
    @Expose var original_language: String = "",
    @Expose var original_title: String = "",
    @Expose var overview: String = "",
    @Expose var popularity: Double = 0.0,
    @Expose var poster_path: String = "",
    @Expose var release_date: String = "",
    @Expose var revenue: Long = 0,
    @Expose var runtime: Int = 0,
    @Expose var status: String = "",
    @Expose var tagline: String = "",
    @Expose var title: String = "",
    @Expose var video: Boolean = false,
    @Expose var vote_average: Double = 0.0,
    @Expose var vote_count: Int = 0,
)