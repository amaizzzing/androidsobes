package com.example.sobes.data.services.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sobes.data.entities.NotesEntity

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NotesEntity)

    @Query("update notesentity set name = :name, text = :text where lat = :lat and lon = :lon")
    suspend fun updateNote(name: String, text: String, lat: Double, lon: Double)

    @Query("update notesentity set text = :text where lat = :lat and lon = :lon")
    suspend fun updateTextByLatLon(lat: Double, lon: Double, text: String)

    @Query("update notesentity set name = :name where lat = :lat and lon = :lon")
    suspend fun updateNameByLatLon(lat: Double, lon: Double, name: String)

    @Query("select * from notesentity where lat = :lat and lon = :lon")
    suspend fun getByLatLon(lat: Double, lon: Double): NotesEntity?

    @Query("select * from notesentity")
    suspend fun getAllNotes(): List<NotesEntity>
}