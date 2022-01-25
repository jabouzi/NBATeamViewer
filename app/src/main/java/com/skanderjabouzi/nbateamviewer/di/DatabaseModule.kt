package com.skanderjabouzi.nbateamviewer.di

import android.content.Context
import androidx.room.Room
import com.skanderjabouzi.nbateamviewer.data.repository.db.PlayersDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideTeamDao(database: TeamDatabase): TeamDao {
        return database.teamDao()
    }

    @Provides
    fun providePlayerDao(database: TeamDatabase): PlayersDao {
        return database.playersDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TeamDatabase {
        return Room.databaseBuilder(
            appContext,
            TeamDatabase::class.java,
            TeamDatabase.DATABASE_NAME
        ).build()
    }
}