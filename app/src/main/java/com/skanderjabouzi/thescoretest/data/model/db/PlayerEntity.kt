package com.skanderjabouzi.thescoretest.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayerEntity (
	@PrimaryKey
	val id: Int,
	val teamId: Int,
	val position : String,
	val number : Int,
	val full_name : String,
	val height : String,
	val weight : String,
	val date_of_birth : String,
	val from : String
)