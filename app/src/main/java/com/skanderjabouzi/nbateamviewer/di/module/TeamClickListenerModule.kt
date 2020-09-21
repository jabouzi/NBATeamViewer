package com.skanderjabouzi.nbateamviewer.di.module

import com.skanderjabouzi.nbateamviewer.domain.listener.TeamClickListener
import com.skanderjabouzi.nbateamviewer.presentation.teams.TeamsListFragment
import dagger.Binds
import dagger.Module

@Module(includes = [TeamsListFragmentModule::class])
abstract class TeamClickListenerModule {
    @Binds
    abstract fun binTeamClickListener(teamsListFragment: TeamsListFragment): TeamClickListener
}