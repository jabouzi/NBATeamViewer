package com.skanderjabouzi.nbateamviewer.presentation.listener

import com.skanderjabouzi.nbateamviewer.data.model.Team

interface TeamClickListener {
    fun onItemClick(team: Team)
}