package com.example.sobes.data.services.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sobes.data.entities.NotesEntity
import com.example.sobes.data.services.database.dao.NotesDao

@Database(
    entities = [NotesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SobesDB: RoomDatabase() {
    abstract val notesDao: NotesDao

    companion object {
        const val DB_NAME = "sobes.db"

        fun newInstance(context: Context) = Room.databaseBuilder(
            context,
            SobesDB::class.java,
            DB_NAME
        )
        .build()
    }
}