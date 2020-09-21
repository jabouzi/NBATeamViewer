package com.skanderjabouzi.nbateamviewer.domain.listener.usecase

import android.util.Log
import com.skanderjabouzi.nbateamviewer.data.model.net.Player
import com.skanderjabouzi.nbateamviewer.data.net.TeamsRepository
import com.skanderjabouzi.nbateamviewer.domain.usecase.PlayerEntityConverter
import com.skanderjabouzi.nbateamviewer.domain.usecase.SortType
import kotlinx.coroutines.*
import okhttp3.internal.wait
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetTeamPlayersUseCase @Inject constructor(val repository: TeamsRepository) {

    suspend fun getTeamPlayers(teamId: Int): List<Player> {
        var players: List<Player>
        withContext(Dispatchers.IO) {
            players = getTeamPlayersFromDb(teamId)
            if (players.isNullOrEmpty()) {
                players = getTeamPlayersFromApi(teamId)
                savePlayersToDb(teamId, players)
            }
        }

        return players
    }

    suspend fun sortByName(teamId: Int): List<Player> {
        val player = getTeamPlayers(teamId)
        if (sortByName == SortType.ASCENDING) {
            sortByName = SortType.DESCENDING
            return player.sortedWith(compareBy({ it.full_name }))
        } else {
            sortByName = SortType.ASCENDING
            return player.sortedWith(compareByDescending({ it.full_name }))
        }
    }

    suspend fun sortByPosition(teamId: Int): List<Player> {
        val player = getTeamPlayers(teamId)
        if (sortByPosition == SortType.ASCENDING) {
            sortByPosition = SortType.DESCENDING
            return player.sortedWith(compareBy({ it.position }))
        } else {
            sortByPosition = SortType.ASCENDING
            return player.sortedWith(compareByDescending({ it.position }))
        }
    }

    suspend fun sortByNumber(teamId: Int): List<Player> {
        val player = getTeamPlayers(teamId)
        if (sortByNumber == SortType.ASCENDING) {
            sortByNumber = SortType.DESCENDING
            return player.sortedWith(compareBy({ convertToInt(it.number) }))
        } else {
            sortByNumber = SortType.ASCENDING
            return player.sortedWith(compareByDescending({ convertToInt(it.number) }))
        }
    }

    suspend fun getTeamPlayersFromApi(teamId: Int): List<Player> {
        var players: List<Player> = listOf()
        players = repository.getPlayers(teamId)

        return players
    }

    suspend fun getTeamPlayersFromDb(teamId: Int): List<Player> {
        var players: List<Player> = listOf()
        players = PlayerEntityConverter.playerEntityListToPlayerList(repository.getSavedPlayers(teamId))

        return players
    }

    suspend private fun savePlayersToDb(teamId: Int, players: List<Player>) {
        repository.savePlayers(PlayerEntityConverter.playerListToPlayerEntityList(teamId, players))
    }

    private fun convertToInt(strNbr: String): Int {
        var res = 0
        try {
            res = strNbr.toInt()
        } catch (e: Exception) {
            Log.e("PlayersUseCase", e.localizedMessage)
        }

        return res
    }

    companion object {
        var sortByName: SortType = SortType.ASCENDING
        var sortByPosition: SortType = SortType.ASCENDING
        var sortByNumber: SortType = SortType.ASCENDING
    }
}