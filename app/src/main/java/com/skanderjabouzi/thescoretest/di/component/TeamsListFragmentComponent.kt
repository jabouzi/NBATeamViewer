package com.skanderjabouzi.thescoretest.di.components

import com.skanderjabouzi.thescoretest.di.module.TeamsListAdapterModule
import com.skanderjabouzi.thescoretest.di.modules.TeamsListViewModelModule
import com.skanderjabouzi.thescoretest.di.modules.ViewModelFactoryModule
import com.skanderjabouzi.thescoretest.di.scope.FragmentScope
import com.skanderjabouzi.thescoretest.presentation.teams.TeamsListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [TeamsListViewModelModule::class, ViewModelFactoryModule::class, TeamsListAdapterModule::class])
interface TeamsListFragmentComponent {
    fun inject(teamsListFragment: TeamsListFragment)
}