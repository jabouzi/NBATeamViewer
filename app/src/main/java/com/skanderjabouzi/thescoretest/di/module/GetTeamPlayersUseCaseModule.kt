package com.skanderjabouzi.thescoretest.di.module

import com.skanderjabouzi.thescoretest.data.net.TeamsRepository
import com.skanderjabouzi.thescoretest.domain.listener.usecase.GetTeamPlayersUseCase
import com.skanderjabouzi.thescoretest.presentation.players.TeamPlayersListAdapter
import dagger.Module
import dagger.Provides

@Module (includes = [TeamsRepositoryModule::class])
class GetTeamPlayersUseCaseModule {
    @Provides
    fun getTeamPlayersUseCase(repository: TeamsRepository): GetTeamPlayersUseCase {
        return GetTeamPlayersUseCase(repository)
    }
}
