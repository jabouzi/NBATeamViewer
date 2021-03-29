package com.skanderjabouzi.nbateamviewer.data.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamModel (
    @PrimaryKey
    val id : Int,
    val name : String,
    val wins : Int,
    val losses : Int
)