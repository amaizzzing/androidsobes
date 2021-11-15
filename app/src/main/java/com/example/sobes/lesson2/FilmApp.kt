package com.example.sobes.lesson2

import android.app.Application
import com.example.sobes.lesson2.di.DaggerFilmComponent
import com.example.sobes.lesson2.di.FilmComponent

class FilmApp: Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
        filmComponent = DaggerFilmComponent
            .builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var instance: FilmApp
        lateinit var filmComponent: FilmComponent
    }
}