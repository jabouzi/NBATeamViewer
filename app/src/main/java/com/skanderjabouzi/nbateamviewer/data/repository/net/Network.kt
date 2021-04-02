package com.skanderjabouzi.nbateamviewer.data.repository.net

import android.content.Context
import com.google.gson.GsonBuilder
import com.skanderjabouzi.nbateamviewer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    fun getRetrofit(context: Context): Retrofit {

        val okhttpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        okhttpClient.addInterceptor(logging)

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val gsonFactory = GsonConverterFactory.create(gson)

        okhttpClient.addInterceptor(logging)
        if (BuildConfig.FLAVOR.equals("mock")) {
            okhttpClient.addInterceptor(MockInterceptor(context))
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonFactory)
            .client(okhttpClient.build())
            .build()

        return retrofit
    }

}