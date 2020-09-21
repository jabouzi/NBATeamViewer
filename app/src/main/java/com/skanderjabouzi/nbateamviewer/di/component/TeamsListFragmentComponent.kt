package com.skanderjabouzi.nbateamviewer.di.components

import com.skanderjabouzi.nbateamviewer.di.module.TeamsListAdapterModule
import com.skanderjabouzi.nbateamviewer.di.modules.TeamsListViewModelModule
import com.skanderjabouzi.nbateamviewer.di.modules.ViewModelFactoryModule
import com.skanderjabouzi.nbateamviewer.di.scope.FragmentScope
import com.skanderjabouzi.nbateamviewer.presentation.teams.TeamsListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [TeamsListViewModelModule::class, ViewModelFactoryModule::class, TeamsListAdapterModule::class])
interface TeamsListFragmentComponent {
    fun inject(teamsListFragment: TeamsListFragment)
}