package com.skanderjabouzi.thescoretest.di.components

import com.skanderjabouzi.thescoretest.di.module.TeamPlayersAdapterModule
import com.skanderjabouzi.thescoretest.di.modules.TeamPlayersViewModelModule
import com.skanderjabouzi.thescoretest.di.modules.ViewModelFactoryModule
import com.skanderjabouzi.thescoretest.di.scope.FragmentScope
import com.skanderjabouzi.thescoretest.presentation.players.TeamPlayersFragment
import com.skanderjabouzi.thescoretest.presentation.players.TeamPlayersViewModel
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [TeamPlayersViewModelModule::class, ViewModelFactoryModule::class, TeamPlayersAdapterModule::class])
interface TeamPlayersFragmentComponent {
    fun inject(teamPlayersFragment: TeamPlayersFragment)
}