package com.yape.recipes.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yape.recipes.R


fun ImageView.loadImage(path: String){
    RequestOptions()
        .placeholder(R.drawable.empty_image)
        .error(R.drawable.empty_image)
        .dontAnimate().also {
            Glide.with(context)
                .load(path)
                .apply(it)
                .into(this)
        }
}