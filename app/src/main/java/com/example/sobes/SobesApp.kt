package com.example.sobes

import android.app.Application
import com.example.sobes.di.AppComponent
import com.example.sobes.di.DaggerAppComponent

class SobesApp: Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var instance: SobesApp
        lateinit var appComponent: AppComponent
    }
}