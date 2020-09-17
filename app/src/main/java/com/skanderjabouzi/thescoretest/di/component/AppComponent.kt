package com.skanderjabouzi.thescoretest.di.components

import com.skanderjabouzi.thescoretest.data.net.RetrofitClient
import com.skanderjabouzi.thescoretest.di.module.TeamsRepositoryModule
import com.skanderjabouzi.thescoretest.di.module.RetrofitModule
import com.skanderjabouzi.thescoretest.di.scope.AppScope
import dagger.Component


@AppScope
@Component(modules = [RetrofitModule::class, TeamsRepositoryModule::class])
interface AppComponent {
    fun getRetrofitClient(): RetrofitClient
    fun getTeamsListFragmentComponent(): TeamsListFragmentComponent
    fun getTeamPlayersFragmentComponent(): TeamPlayersFragmentComponent
}