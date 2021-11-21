package com.example.sobes.lesson2.di.modules

import androidx.lifecycle.ViewModel
import com.example.sobes.lesson2.di.ViewModelKey
import com.example.sobes.lesson2.ui.viewmodels.MovieViewModel
import com.example.sobes.lesson2.ui.viewmodels.TopRatedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TopRatedViewModel::class)
    abstract fun bindTopRatedViewModel(topRatedViewModel: TopRatedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel
}