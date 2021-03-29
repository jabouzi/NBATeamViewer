package com.skanderjabouzi.nbateamviewer.presentation.listener

import com.skanderjabouzi.nbateamviewer.domain.entity.Team

interface TeamClickListener {
    fun onItemClick(team: Team)
}