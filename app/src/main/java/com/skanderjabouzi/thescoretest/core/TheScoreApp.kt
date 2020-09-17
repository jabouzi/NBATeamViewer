package com.skanderjabouzi.thescoretest.core

import android.app.Application
import com.skanderjabouzi.thescoretest.data.db.TeamDatabase
import com.skanderjabouzi.thescoretest.di.components.AppComponent
import com.skanderjabouzi.thescoretest.di.components.DaggerAppComponent

class TheScoreApp: Application() {
    lateinit var db: TeamDatabase
    val appComponent: AppComponent by lazy { initDagger() }

    private fun initDagger(): AppComponent {
        val component = DaggerAppComponent.builder()
            .build()
        return component
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        db = TeamDatabase.getInstance(this)
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: TheScoreApp
    }
}