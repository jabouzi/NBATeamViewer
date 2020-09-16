package com.skanderjabouzi.thescoretest.di.module

import com.skanderjabouzi.thescoretest.presentation.players.TeamPlayersListAdapter
import dagger.Module
import dagger.Provides

@Module
class TeamPlayersAdapterModule {
    @Provides
    fun getTeamPlayersListAdapter(): TeamPlayersListAdapter {
        return TeamPlayersListAdapter()
    }
}
