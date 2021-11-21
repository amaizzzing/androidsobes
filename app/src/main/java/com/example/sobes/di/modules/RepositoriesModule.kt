package com.example.sobes.di.modules

import com.example.sobes.data.repositories.notereposiroty.INoteRepository
import com.example.sobes.data.repositories.notereposiroty.NoteRepository
import com.example.sobes.data.services.database.db.SobesDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Singleton
    @Provides
    fun noteRepository(db: SobesDB): INoteRepository = NoteRepository(db)
}