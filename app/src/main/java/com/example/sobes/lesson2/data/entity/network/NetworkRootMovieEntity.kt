package com.example.sobes.lesson2.data.entity.network

import com.google.gson.annotations.Expose

data class NetworkRootMovieEntity(
    @Expose var page: Int = 0,
    @Expose var results: List<NetworkMovieEntity>? = null,
    @Expose var total_pages: Int = 0,
    @Expose var total_results: Int = 0
)