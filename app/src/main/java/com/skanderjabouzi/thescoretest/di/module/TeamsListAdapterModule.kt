package com.skanderjabouzi.thescoretest.di.module

import com.skanderjabouzi.thescoretest.domain.listener.TeamClickListener
import com.skanderjabouzi.thescoretest.presentation.teams.TeamsListAdapter
import dagger.Module
import dagger.Provides

@Module(includes = [TeamClickListenerModule::class])
class TeamsListAdapterModule {

    @Provides
    fun getTeamsListAdapter(teamClickListener: TeamClickListener): TeamsListAdapter {
        return TeamsListAdapter(teamClickListener)
    }

}