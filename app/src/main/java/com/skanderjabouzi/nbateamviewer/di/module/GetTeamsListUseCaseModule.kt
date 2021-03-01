package com.skanderjabouzi.nbateamviewer.di.module

import com.skanderjabouzi.nbateamviewer.domain.net.TeamsRepository
import com.skanderjabouzi.nbateamviewer.domain.listener.usecase.GetTeamPlayersUseCase
import com.skanderjabouzi.nbateamviewer.domain.listener.usecase.GetTeamsListUseCase
import com.skanderjabouzi.nbateamviewer.presentation.players.TeamPlayersListAdapter
import dagger.Module
import dagger.Provides

@Module (includes = [TeamsRepositoryModule::class])
class GetTeamsListUseCaseModule {
    @Provides
    fun getTeamPlayersUseCase(repository: TeamsRepository): GetTeamsListUseCase {
        return GetTeamsListUseCase(repository)
    }
}
