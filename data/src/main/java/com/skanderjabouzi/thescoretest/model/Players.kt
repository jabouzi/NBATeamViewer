package com.skanderjabouzi.thescoretest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Players (
	@PrimaryKey
	val id : Int,
	val players : List<Players>
)