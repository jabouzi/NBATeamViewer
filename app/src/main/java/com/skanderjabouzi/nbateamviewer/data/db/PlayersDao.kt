package com.skanderjabouzi.nbateamviewer.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skanderjabouzi.nbateamviewer.data.model.db.PlayerEntity

@Dao
interface PlayersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(players: PlayerEntity)

    @Query("SELECT * FROM playerentity WHERE teamId = :id")
    suspend fun getPlayers(id: Int?): List<PlayerEntity>
}