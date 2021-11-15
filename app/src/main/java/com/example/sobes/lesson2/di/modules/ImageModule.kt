package com.example.sobes.lesson2.di.modules

import android.widget.ImageView
import com.example.sobes.lesson2.data.services.GlideImageViewLoader
import com.example.sobes.lesson2.data.services.IImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageModule {
    @Provides
    @Singleton
    fun imageLoader(): IImageLoader<ImageView> =
        GlideImageViewLoader()
}