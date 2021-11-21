package com.example.sobes.di.modules

import com.example.sobes.ui.adapters.presenters.INotesAdapterPresenter
import com.example.sobes.ui.adapters.presenters.NotesAdapterPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {
    @Singleton
    @Provides
    fun notesAdapterPresenter(): INotesAdapterPresenter = NotesAdapterPresenter()
}