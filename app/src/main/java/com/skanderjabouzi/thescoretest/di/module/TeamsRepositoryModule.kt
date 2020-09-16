package com.skanderjabouzi.thescoretest.di.module

import com.skanderjabouzi.thescoretest.data.net.TeamsRepository
import com.skanderjabouzi.thescoretest.data.repo.TeamsRepositoryImpl
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