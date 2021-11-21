package com.example.sobes.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["lat","lon"])
data class NotesEntity (
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var name: String = "",
    var text: String = ""
)