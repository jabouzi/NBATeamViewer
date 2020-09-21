package com.skanderjabouzi.nbateamviewer.di.components

import com.skanderjabouzi.nbateamviewer.data.net.RetrofitClient
import com.skanderjabouzi.nbateamviewer.di.module.TeamsRepositoryModule
import com.skanderjabouzi.nbateamviewer.di.module.RetrofitModule
import com.skanderjabouzi.nbateamviewer.di.scope.AppScope
import dagger.Component


@AppScope
@Component(modules = [RetrofitModule::class, TeamsRepositoryModule::class])
interface AppComponent {
    fun getRetrofitClient(): RetrofitClient
    fun getTeamsListFragmentComponent(): TeamsListFragmentComponent
    fun getTeamPlayersFragmentComponent(): TeamPlayersFragmentComponent
}