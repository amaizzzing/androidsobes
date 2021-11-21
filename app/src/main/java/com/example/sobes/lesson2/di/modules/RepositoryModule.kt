package com.example.sobes.lesson2.di.modules

import com.example.sobes.lesson2.data.api.IFilmDatasource
import com.example.sobes.lesson2.data.repositories.INetworkMovieRepository
import com.example.sobes.lesson2.data.repositories.NetworkMovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun networkMovieRepository(api: IFilmDatasource): INetworkMovieRepository = NetworkMovieRepository(api)
}