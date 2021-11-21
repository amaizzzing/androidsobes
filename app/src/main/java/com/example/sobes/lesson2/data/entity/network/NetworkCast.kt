package com.example.sobes.lesson2.data.entity.network

import com.google.gson.annotations.Expose

data class NetworkCast(
    @Expose var adult: Boolean = false,
    @Expose var gender: Int = 0,
    @Expose var id: Int = 0,
    @Expose var known_for_department: String = "",
    @Expose var name: String = "",
    @Expose var original_name: String = "",
    @Expose var popularity: Double = 0.0,
    @Expose var profile_path: String = "",
    @Expose var cast_id: Int = 0,
    @Expose var character: String = "",
    @Expose var credit_id: String = "",
    @Expose var order: Int = 0,
)
