package ru.bslab.test.avdeevav.repository.loader

import android.widget.ImageView

import com.squareup.picasso.Picasso

class ImageLoader {

    companion object {

        fun load(fromUrl: String, intoView: ImageView) {

            // Images do not download via https (Android API < 21)
            // TODO: Picasso.Builder(context).downloader(OkHttp3Downloader(OkHttpClient)).build()

            Picasso.get().load(fromUrl).into(intoView)
        }
    }
}