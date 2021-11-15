package com.example.sobes.lesson2.di

import com.example.sobes.lesson2.FilmApp
import com.example.sobes.lesson2.di.modules.ApiModule
import com.example.sobes.lesson2.di.modules.ImageModule
import com.example.sobes.lesson2.di.modules.RepositoryModule
import com.example.sobes.lesson2.di.modules.ViewModelModule
import com.example.sobes.lesson2.ui.fragments.MovieFragment
import com.example.sobes.lesson2.ui.fragments.TopRatedFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        ImageModule::class
    ]
)
interface FilmComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: FilmApp): Builder

        fun build(): FilmComponent
    }

    fun inject(topRatedFragment: TopRatedFragment)

    fun inject(movieFragment: MovieFragment)
}