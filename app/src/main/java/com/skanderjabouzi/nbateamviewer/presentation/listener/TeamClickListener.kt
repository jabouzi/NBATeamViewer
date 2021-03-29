package com.skanderjabouzi.nbateamviewer.presentation.listener

import com.skanderjabouzi.nbateamviewer.domain.model.Team

interface TeamClickListener {
    fun onItemClick(team: Team)
}