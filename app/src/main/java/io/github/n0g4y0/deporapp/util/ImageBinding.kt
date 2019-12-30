package io.github.n0g4y0.deporapp.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object ImageBinding {
    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun setImageUrl(imageView: ImageView, url: String) {
        if (url.isNotEmpty()) {
            Picasso.get()
                .load(url)
                .resize(200,200)
                .centerInside()
                .into(imageView)
        }
    }
}