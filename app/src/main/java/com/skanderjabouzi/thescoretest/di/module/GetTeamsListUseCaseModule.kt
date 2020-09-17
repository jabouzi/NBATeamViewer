package com.skanderjabouzi.thescoretest.di.module

import com.skanderjabouzi.thescoretest.data.net.TeamsRepository
import com.skanderjabouzi.thescoretest.domain.listener.usecase.GetTeamPlayersUseCase
import com.skanderjabouzi.thescoretest.domain.listener.usecase.GetTeamsListUseCase
import com.skanderjabouzi.thescoretest.presentation.players.TeamPlayersListAdapter
import dagger.Module
import dagger.Provides

@Module (includes = [TeamsRepositoryModule::class])
class GetTeamsListUseCaseModule {
    @Provides
    fun getTeamPlayersUseCase(repository: TeamsRepository): GetTeamsListUseCase {
        return GetTeamsListUseCase(repository)
    }
}
