package com.skanderjabouzi.nbateamviewer.di.module

import com.skanderjabouzi.nbateamviewer.data.net.TeamsRepository
import com.skanderjabouzi.nbateamviewer.data.repo.TeamsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class TeamsRepositoryModule {
    @Provides
    fun provideRepository(): TeamsRepository {
        return TeamsRepositoryImpl()
    }
}