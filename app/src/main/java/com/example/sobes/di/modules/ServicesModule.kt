package com.example.sobes.di.modules

import androidx.room.Room
import com.example.sobes.SobesApp
import com.example.sobes.data.services.database.db.SobesDB
import com.example.sobes.data.services.googlemapsservice.GoogleMapsService
import com.example.sobes.data.services.googlemapsservice.IMapsService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServicesModule {
    @Singleton
    @Provides
    fun database(app: SobesApp):SobesDB =
        Room.databaseBuilder(
            app,
            SobesDB::class.java,
            SobesDB.DB_NAME
        ).build()

    @Singleton
    @Provides
    fun mapService(): IMapsService = GoogleMapsService()
}