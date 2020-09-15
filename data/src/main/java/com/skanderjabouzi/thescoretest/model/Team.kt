package com.skanderjabouzi.thescoretest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Team (
    @PrimaryKey
    val id : Int,
    val name : String,
    val wins : Int,
    val losses : Int
)