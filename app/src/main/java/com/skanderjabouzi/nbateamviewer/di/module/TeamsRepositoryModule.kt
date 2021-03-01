package com.skanderjabouzi.nbateamviewer.di.module

import com.skanderjabouzi.nbateamviewer.domain.net.TeamsRepository
import com.skanderjabouzi.nbateamviewer.domain.net.TeamsRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class TeamsRepositoryModule {
    @Provides
    fun provideRepository(): TeamsRepository {
        return TeamsRepositoryImpl()
    }
}