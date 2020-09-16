package com.skanderjabouzi.thescoretest.di.module

import com.skanderjabouzi.thescoretest.domain.listener.TeamClickListener
import com.skanderjabouzi.thescoretest.presentation.teams.TeamsListFragment
import dagger.Binds
import dagger.Module

@Module(includes = [TeamsListFragmentModule::class])
abstract class TeamClickListenerModule {
    @Binds
    abstract fun bindMovieClickListener(movieListFragment: TeamsListFragment): TeamClickListener

}