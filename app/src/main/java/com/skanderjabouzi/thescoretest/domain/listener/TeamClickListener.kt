package com.skanderjabouzi.thescoretest.domain.listener

import com.skanderjabouzi.thescoretest.data.model.net.Team

interface TeamClickListener {
    fun onItemClick(team: Team)
}