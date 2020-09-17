package com.skanderjabouzi.thescoretest.domain.listener.usecase

import com.skanderjabouzi.thescoretest.data.model.net.Player
import com.skanderjabouzi.thescoretest.data.net.TeamsRepository
import javax.inject.Inject

class GetTeamPlayersUseCase @Inject constructor(val repository: TeamsRepository) {
    suspend fun getTeamPlayers(teamId: Int): List<Player> {
        return listOf()
    }

    suspend private fun getTeamPlayersFromApi(teamId: Int): List<Player> {
        return listOf()
    }

    suspend private fun getTeamPlayersFromDb(teamId: Int): List<Player> {
        return listOf()
    }

    suspend private fun savePlayersToDb(players: List<Player>) {

    }
}