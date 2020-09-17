package com.skanderjabouzi.thescoretest.domain.listener.usecase

import com.skanderjabouzi.thescoretest.data.model.net.Player
import com.skanderjabouzi.thescoretest.data.net.TeamsRepository
import com.skanderjabouzi.thescoretest.domain.usecase.PlayerEntityConverter
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetTeamPlayersUseCase @Inject constructor(val repository: TeamsRepository): CoroutineScope {

    private val parentJob: Job = SupervisorJob()

    suspend fun getTeamPlayers(teamId: Int): List<Player> {
        var players: List<Player> = listOf()
        players = getTeamPlayersFromDb(teamId)
        if (players.isNullOrEmpty()) {
            players = getTeamPlayersFromApi(teamId)
            savePlayersToDb(teamId, players)
        }

        return players
    }

    suspend private fun getTeamPlayersFromApi(teamId: Int): List<Player> {
        var players: List<Player> = listOf()
        launch {
            players = repository.getPlayers(teamId)
        }

        return players
    }

    suspend private fun getTeamPlayersFromDb(teamId: Int): List<Player> {
        var players: List<Player> = listOf()
        launch {
            players = PlayerEntityConverter.playerEntityListToPlayerList(repository.getSavedPlayers(teamId))
        }

        return players
    }

    suspend private fun savePlayersToDb(teamId: Int, players: List<Player>) {
        launch {
            repository.savePlayers(PlayerEntityConverter.playerListToPlayerEntityList(teamId, players))
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

}