package com.skanderjabouzi.nbateamviewer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayerEntity (
	@PrimaryKey(autoGenerate = true)
	val id: Int? = null,
	val teamId: Int,
	val position : String,
	val number : String,
	val full_name : String,
	val height : String,
	val weight : String,
	val date_of_birth : String,
	val from : String
)