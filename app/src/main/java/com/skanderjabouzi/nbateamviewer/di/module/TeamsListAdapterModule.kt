package com.skanderjabouzi.nbateamviewer.di.module

import com.skanderjabouzi.nbateamviewer.domain.listener.TeamClickListener
import com.skanderjabouzi.nbateamviewer.presentation.teams.TeamsListAdapter
import dagger.Module
import dagger.Provides

@Module(includes = [TeamClickListenerModule::class])
class TeamsListAdapterModule {

    @Provides
    fun getTeamsListAdapter(teamClickListener: TeamClickListener): TeamsListAdapter {
        return TeamsListAdapter(teamClickListener)
    }

}