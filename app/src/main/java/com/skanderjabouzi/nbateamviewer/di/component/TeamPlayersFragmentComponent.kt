package com.skanderjabouzi.nbateamviewer.di.components

import com.skanderjabouzi.nbateamviewer.di.module.TeamPlayersAdapterModule
import com.skanderjabouzi.nbateamviewer.di.modules.TeamPlayersViewModelModule
import com.skanderjabouzi.nbateamviewer.di.modules.ViewModelFactoryModule
import com.skanderjabouzi.nbateamviewer.di.scope.FragmentScope
import com.skanderjabouzi.nbateamviewer.presentation.players.TeamPlayersFragment
import com.skanderjabouzi.nbateamviewer.presentation.players.TeamPlayersViewModel
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [TeamPlayersViewModelModule::class, ViewModelFactoryModule::class, TeamPlayersAdapterModule::class])
interface TeamPlayersFragmentComponent {
    fun inject(teamPlayersFragment: TeamPlayersFragment)
}