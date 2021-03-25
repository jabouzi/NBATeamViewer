package com.skanderjabouzi.nbateamviewer.core

import android.app.Application
import com.skanderjabouzi.nbateamviewer.data.db.TeamDatabase

class App: Application() {
    lateinit var db: TeamDatabase

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