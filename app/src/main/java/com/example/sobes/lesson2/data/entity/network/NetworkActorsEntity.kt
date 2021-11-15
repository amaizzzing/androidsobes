package com.example.sobes.lesson2.data.entity.network

import com.google.gson.annotations.Expose

data class NetworkActorsEntity(
    @Expose var id: Int = 0,
    @Expose var cast: List<NetworkCast> = listOf()
)