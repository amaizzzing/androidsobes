package com.example.sobes.lesson2.data.services

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class GlideImageViewLoader(): IImageLoader<ImageView> {
    override fun loadInto(source: String, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            .load(source)
            .into(container)
    }

    override fun loadIntoWithRoundCorners(source: String, container: ImageView, corner: Int) {
        Glide.with(container.context)
            .asBitmap()
            .load(source)
            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(corner)))
            .into(container)
    }
}