package com.example.sobes.lesson2.data.entity.uimodels

data class MovieInfoModel(
    var backdropImage: String = "",
    var posterImage: String = "",
    var title: String = "",
    var released_date: String = "",
    var duration: String = "",
    var genres: String = "",
    var description: String = "",
    var actorList: List<String> = listOf()
)