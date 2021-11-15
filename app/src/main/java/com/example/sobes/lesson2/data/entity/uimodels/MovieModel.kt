package com.example.sobes.lesson2.data.entity.uimodels

import com.example.sobes.lesson2.IMAGE_URL
import com.example.sobes.lesson2.data.entity.network.NetworkMovieEntity
import com.example.sobes.toCorrectDate

data class MovieModel(
    var idMovie: Int = 0,
    var movieName: String = "",
    var movieDate: String = "",
    var movieRating: Float = 0.0f,
    var image: String = ""
) {
    constructor(networkMovieEntity: NetworkMovieEntity): this() {
        with(networkMovieEntity) {
            idMovie = id
            movieName = title
            movieDate = release_date.toCorrectDate()
            movieRating = vote_average.toFloat()
            image = "$IMAGE_URL$poster_path"
        }
    }
}