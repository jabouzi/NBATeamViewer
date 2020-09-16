package com.skanderjabouzi.thescoretest.di.modules

import androidx.lifecycle.ViewModel
import com.skanderjabouzi.thescoretest.di.scope.ViewModelKey
import com.skanderjabouzi.thescoretest.presentation.teams.TeamsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TeamsListViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TeamsListViewModel::class)
    abstract fun bindsMainViewModelModule(viewModel: TeamsListViewModel): ViewModel
}