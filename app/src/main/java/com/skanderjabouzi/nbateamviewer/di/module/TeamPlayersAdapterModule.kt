package com.skanderjabouzi.nbateamviewer.di.module

import com.skanderjabouzi.nbateamviewer.presentation.players.TeamPlayersListAdapter
import dagger.Module
import dagger.Provides

@Module
class TeamPlayersAdapterModule {
    @Provides
    fun getTeamPlayersListAdapter(): TeamPlayersListAdapter {
        return TeamPlayersListAdapter()
    }
}
