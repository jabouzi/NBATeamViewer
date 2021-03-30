package com.skanderjabouzi.nbateamviewer.data.repository.net

import android.content.Context
import com.google.gson.GsonBuilder
//import com.skanderjabouzi.nbateamviewer.data.BuildConfig
//import com.skanderjabouzi.nbateamviewer.data.
//import com.skanderjabouzi.nbateamviewer.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//TODO Refactor the repo and fix the buildconfig

object Network {
    fun getRetrofit(context: Context): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val gsonFactory = GsonConverterFactory.create(gson)

        val logging = HttpLoggingInterceptor()
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(logging)
//        if (BuildConfig.FLAVOR.equals("mock")) {
//            builder.addInterceptor(MockInterceptor(context))
//        }
        val okHttpClient = builder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://skanderjabouzi.com/nbateamviewer/")
//            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonFactory)
            .client(okHttpClient)
            .build()

        return retrofit
    }

}