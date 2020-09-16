package com.skanderjabouzi.thescoretest.di.module

import com.skanderjabouzi.thescoretest.presentation.teams.TeamsListFragment
import dagger.Module
import dagger.Provides

@Module
class TeamsListFragmentModule {
    @Provides
    fun provideMovieListFragment(): TeamsListFragment {
        return TeamsListFragment()
    }
}