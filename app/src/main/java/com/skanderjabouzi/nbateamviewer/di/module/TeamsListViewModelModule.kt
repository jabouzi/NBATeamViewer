package com.skanderjabouzi.nbateamviewer.di.modules

import androidx.lifecycle.ViewModel
import com.skanderjabouzi.nbateamviewer.di.scope.ViewModelKey
import com.skanderjabouzi.nbateamviewer.presentation.teams.TeamsListViewModel
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