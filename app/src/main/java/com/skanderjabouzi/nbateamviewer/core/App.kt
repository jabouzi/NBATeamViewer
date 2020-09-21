package com.skanderjabouzi.nbateamviewer.core

import android.app.Application
import com.skanderjabouzi.nbateamviewer.data.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.di.components.AppComponent
import com.skanderjabouzi.nbateamviewer.di.components.DaggerAppComponent

class App: Application() {
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
        lateinit var INSTANCE: App
    }
}