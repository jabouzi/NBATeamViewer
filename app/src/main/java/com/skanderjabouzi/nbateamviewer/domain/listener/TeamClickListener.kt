package com.skanderjabouzi.nbateamviewer.domain.listener

import com.skanderjabouzi.nbateamviewer.data.model.net.Team

interface TeamClickListener {
    fun onItemClick(team: Team)
}