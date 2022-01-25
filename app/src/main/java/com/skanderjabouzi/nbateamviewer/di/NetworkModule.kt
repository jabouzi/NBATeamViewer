package com.skanderjabouzi.nbateamviewer.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.skanderjabouzi.nbateamviewer.BuildConfig
import com.skanderjabouzi.nbateamviewer.data.repository.net.MockInterceptor
import com.skanderjabouzi.nbateamviewer.data.repository.net.TeamsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideGason(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideOkHttp(@ApplicationContext context: Context): OkHttpClient {
        val okhttpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        okhttpClient.addInterceptor(logging)
        if (BuildConfig.FLAVOR.equals("mock")) {
            okhttpClient.addInterceptor(MockInterceptor(context))
        }
        return okhttpClient.build()
    }

    @Provides
    fun provideRetrofit(okhttpClient: OkHttpClient,
                        gsonFactory: GsonConverterFactory
    ): TeamsApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonFactory)
            .client(okhttpClient)
            .build()
            .create(TeamsApi::class.java)

        return retrofit
    }
}