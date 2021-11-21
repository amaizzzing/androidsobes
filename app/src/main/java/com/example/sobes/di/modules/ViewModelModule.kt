package com.example.sobes.di.modules

import androidx.lifecycle.ViewModel
import com.example.sobes.di.ViewModelKey
import com.example.sobes.ui.viewmodels.AddMarkerDialogViewModel
import com.example.sobes.ui.viewmodels.MapFragmentViewModel
import com.example.sobes.ui.viewmodels.NotesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel::class)
    abstract fun bindNoteViewModel(notesViewModel: NotesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddMarkerDialogViewModel::class)
    abstract fun bindMarkerViewModel(addMarkerDialogViewModel: AddMarkerDialogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapFragmentViewModel::class)
    abstract fun bindMapFragmentViewModel(mapFragmentViewModel: MapFragmentViewModel): ViewModel
}