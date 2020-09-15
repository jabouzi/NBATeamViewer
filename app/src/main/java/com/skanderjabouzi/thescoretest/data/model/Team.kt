package com.skanderjabouzi.thescoretest.data.model

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