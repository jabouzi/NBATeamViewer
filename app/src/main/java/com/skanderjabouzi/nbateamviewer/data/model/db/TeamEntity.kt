package com.skanderjabouzi.nbateamviewer.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamEntity (
    @PrimaryKey
    val id : Int,
    val name : String,
    val wins : Int,
    val losses : Int
)