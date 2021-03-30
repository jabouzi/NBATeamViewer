package com.skanderjabouzi.nbateamviewer.domain.usecase

import android.app.Application
import android.util.Log
import com.skanderjabouzi.nbateamviewer.domain.model.Player
import com.skanderjabouzi.nbateamviewer.domain.model.Players
import com.skanderjabouzi.nbateamviewer.domain.*
import com.skanderjabouzi.nbateamviewer.domain.gateway.TeamPlayersRepository
import kotlinx.coroutines.*
import java.io.IOException

class TeamPlayersUseCase(val application: Application): UseCase() {

    var repository = TeamPlayersRepository(application)

    suspend fun getTeamPlayers(teamId: Int) {
        val player = getTeamPlayersUsecase(teamId)
        playersList.value = player
    }

    suspend fun sortByName(teamId: Int) {
        val player = getTeamPlayersUsecase(teamId)
        if (sortByName == SortType.ASCENDING) {
            sortByName = SortType.DESCENDING
            playersList.value = player.sortedWith(compareBy({ it.full_name }))
        } else {
            sortByName = SortType.ASCENDING
            playersList.value = player.sortedWith(compareByDescending({ it.full_name }))
        }
    }

    suspend fun sortByPosition(teamId: Int) {
        val player = getTeamPlayersUsecase(teamId)
        if (sortByPosition == SortType.ASCENDING) {
            sortByPosition = SortType.DESCENDING
            playersList.value = player.sortedWith(compareBy({ it.position }))
        } else {
            sortByPosition = SortType.ASCENDING
            playersList.value = player.sortedWith(compareByDescending({ it.position }))
        }
    }

    suspend fun sortByNumber(teamId: Int) {
        val player = getTeamPlayersUsecase(teamId)
        if (sortByNumber == SortType.ASCENDING) {
            sortByNumber = SortType.DESCENDING
            playersList.value = player.sortedWith(compareBy({ convertToInt(it.number) }))
        } else {
            sortByNumber = SortType.ASCENDING
            playersList.value = player.sortedWith(compareByDescending({ convertToInt(it.number) }))
        }
    }

    suspend fun getTeamPlayersFromApi(teamId: Int): ResultState? {
        return try {
            val response = repository.getPlayers(teamId)
            if (response.isSuccessful) {
                response.body()?.let { ResultState.Success(it) }
            } else {
                validateResponse(response.code(), response.message())
            }
        } catch (error: IOException) {
            ResultState.NetworkException(error.message!!)
        }
    }

    suspend fun getTeamPlayersFromDb(teamId: Int): ResultState {
        val players =
                PlayerEntityConverter.playerEntityListToPlayerList(repository.getSavedPlayers(teamId))
        return if (players.isNullOrEmpty()) {
            ResultState.InvalidData
        } else {
            ResultState.Success(players)
        }
    }

    suspend private fun savePlayersToDb(teamId: Int, players: List<Player>) {
        repository.savePlayers(PlayerEntityConverter.playerListToPlayerEntityList(teamId, players))
    }

    suspend private fun getTeamPlayersUsecase(teamId: Int): List<Player> {
        var players: List<Player> = emptyList()
        withContext(Dispatchers.IO) {
            getTeamPlayersFromDb(teamId).let {
                when(it) {
                    is ResultState.Success -> {
                        players = it.data as List<Player>
                    }
                    is ResultState.InvalidData -> {
                        getTeamPlayersFromApi(teamId)?.let {
                            when (it) {
                                is ResultState.Success -> {
                                    it.data
                                    players = (it.data as Players).players
                                    savePlayersToDb(teamId, players)
                                    playersList.postValue(players)
                                    error.postValue("")
                                }
                                else -> handleCharactersResult(it)
                            }
                        }
                    }
                    else -> handleCharactersResult(it)
                }
            }
        }

        return players
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