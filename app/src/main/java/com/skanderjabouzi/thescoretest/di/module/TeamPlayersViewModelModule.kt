package com.skanderjabouzi.thescoretest.di.modules

import androidx.lifecycle.ViewModel
import com.skanderjabouzi.thescoretest.di.scope.ViewModelKey
import com.skanderjabouzi.thescoretest.presentation.players.TeamPlayersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TeamPlayersViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TeamPlayersViewModel::class)
    abstract fun bindsAddViewModelModule(viewModel: TeamPlayersViewModel): ViewModel
}