package com.skanderjabouzi.nbateamviewer.di.module

import com.skanderjabouzi.nbateamviewer.presentation.teams.TeamsListFragment
import dagger.Module
import dagger.Provides

@Module
class TeamsListFragmentModule {
    @Provides
    fun provideMovieListFragment(): TeamsListFragment {
        return TeamsListFragment()
    }
}