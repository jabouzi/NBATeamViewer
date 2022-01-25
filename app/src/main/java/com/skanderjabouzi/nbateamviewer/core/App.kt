package com.skanderjabouzi.nbateamviewer.core

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class App: Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
    }
}