package com.skanderjabouzi.nbateamviewer.domain.entity

import java.io.Serializable

data class Team (
    val id : Int,
    val name : String,
    val wins : Int,
    val losses : Int
) : Serializable