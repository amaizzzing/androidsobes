package com.example.sobes.lesson2.data.entity.network

import com.google.gson.annotations.Expose

data class NetworkMovieGenre(
    @Expose var id: Int = 0,
    @Expose var name: String = ""
)