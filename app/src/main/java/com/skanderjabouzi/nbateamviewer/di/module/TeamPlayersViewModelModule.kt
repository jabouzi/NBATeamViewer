package com.skanderjabouzi.nbateamviewer.di.modules

import androidx.lifecycle.ViewModel
import com.skanderjabouzi.nbateamviewer.di.scope.ViewModelKey
import com.skanderjabouzi.nbateamviewer.presentation.players.TeamPlayersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TeamPlayersViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TeamPlayersViewModel::class)
    abstract fun bindsTeamPlayersViewModelModule(viewModel: TeamPlayersViewModel): ViewModel
}