package io.github.n0g4y0.deporapp.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class CargadorImagen {

    fun cargarImagen(context: Context, imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .into(imageView)
    }

}