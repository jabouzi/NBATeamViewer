package com.skanderjabouzi.nbateamviewer.di.module

import com.google.gson.GsonBuilder
import com.skanderjabouzi.nbateamviewer.BuildConfig
import com.skanderjabouzi.nbateamviewer.data.net.MockInterceptor
import com.skanderjabouzi.nbateamviewer.di.scope.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class RetrofitModule {
    @AppScope
    @Provides
    fun provideHttpLogginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @AppScope
    @Provides
    fun provideGsonFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @AppScope
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(logging)
        if (BuildConfig.FLAVOR.equals("mock")) {
            builder.addInterceptor(MockInterceptor())
        }
        return builder.build()
    }

    @AppScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonFactory: GsonConverterFactory): Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(gsonFactory)
                .client(okHttpClient)
                .build()

        return retrofit
    }
}