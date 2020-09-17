package com.skanderjabouzi.thescoretest.domain.listener.usecase

import com.skanderjabouzi.thescoretest.data.model.net.Player
import com.skanderjabouzi.thescoretest.data.model.net.Team
import com.skanderjabouzi.thescoretest.data.net.TeamsRepository
import javax.inject.Inject

class GetTeamsListUseCase @Inject constructor(val repository: TeamsRepository) {
    suspend fun getTeams(): List<Team> {
        return listOf()
    }

    suspend private fun getTeamsFromApi(): List<Team> {
        return listOf()
    }

    suspend private fun getTeamsFromDb(): List<Team> {
        return listOf()
    }

    suspend private fun saveTeamsToDb(teams: List<Team>) {

    }
}