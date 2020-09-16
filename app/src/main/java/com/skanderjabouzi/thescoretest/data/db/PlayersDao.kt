package com.skanderjabouzi.thescoretest.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skanderjabouzi.thescoretest.data.model.db.PlayerEntity

@Dao
interface PlayersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(players: PlayerEntity)

    @Query("SELECT * FROM playerentity WHERE id = :id")
    fun getPlayers(id: Int?): List<PlayerEntity>
}