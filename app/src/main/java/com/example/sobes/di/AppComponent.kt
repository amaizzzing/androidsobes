package com.example.sobes.di

import com.example.sobes.SobesApp
import com.example.sobes.di.modules.PresenterModule
import com.example.sobes.di.modules.RepositoriesModule
import com.example.sobes.di.modules.ServicesModule
import com.example.sobes.di.modules.ViewModelModule
import com.example.sobes.ui.dialogs.AddMarkerDialog
import com.example.sobes.ui.fragments.MapsFragment
import com.example.sobes.ui.fragments.NotesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RepositoriesModule::class,
    ServicesModule::class,
    ViewModelModule::class,
    PresenterModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: SobesApp): Builder

        fun build(): AppComponent
    }

    fun inject(addMarkerDialog: AddMarkerDialog)

    fun inject(mapsFragment: MapsFragment)

    fun inject(notesFragment: NotesFragment)
}