package com.skanderjabouzi.thescoretest.di.modules

import com.skanderjabouzi.thescoretest.di.scope.AppScope
import com.skanderjabouzi.thescoretest.BuildConfig
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
        return GsonConverterFactory.create()
    }

    @AppScope
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(logging)
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