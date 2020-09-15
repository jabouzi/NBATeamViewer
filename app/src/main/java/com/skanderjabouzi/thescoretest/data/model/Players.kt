package com.skanderjabouzi.thescoretest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Players (
	@PrimaryKey
	val id : Int,
	val players : List<Players>
)