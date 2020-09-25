package com.imran.cavista.view.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.imran.cavista.model.Image

/**
 * Created by imran on 2020-09-25.
 */

@BindingAdapter("app:loadImage")
fun loadImage(imageView: AppCompatImageView, images: List<Image>?) {
    if (images == null || images.isEmpty()) {
        return
    }
    Glide.with(imageView.context).load(images[0].link).also {
        it.apply(RequestOptions.centerCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL))
        it.into(imageView)
    }
}
