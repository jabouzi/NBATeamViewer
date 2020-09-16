package com.skanderjabouzi.thescoretest.core

import android.app.Application
import com.skanderjabouzi.thescoretest.data.db.TeamDatabase

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
        db = MovieDatabase.getInstance(this)
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: App
    }
}