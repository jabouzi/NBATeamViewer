package com.skanderjabouzi.nbateamviewer.domain.usecase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.skanderjabouzi.nbateamviewer.domain.gateway.TeamPlayersRepository
import com.skanderjabouzi.nbateamviewer.data.model.Player
import com.skanderjabouzi.nbateamviewer.data.model.Players
import kotlinx.coroutines.flow.collect
import java.io.IOException

class TeamPlayersUseCase(val repository: TeamPlayersRepository): UseCase() {
    val playersList = MutableLiveData<List<Player>>()

    suspend fun getTeamPlayers(teamId: Int) {
        repository.getSavedPlayers(teamId).collect { playersFlow ->
            if (!playersFlow.isNullOrEmpty()) {
                playersList.value = PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
            } else {
                getRequestFromApi(repository.getPlayers(teamId))?.let {
                    when (it) {
                        is ResultState.Success -> {
                            val players = (it.data as Players).players
                            savePlayersToDb(teamId, players)
                            playersList.postValue(players)
                        }
                        else -> error.postValue((it as ResultState.Error).error)
                    }
                }
            }
        }
    }

    suspend fun sortBy(teamId: Int, field: String) {
        if (sortName == SortType.ASCENDING) {
            sortName = SortType.DESCENDING
        }
        repository.getSavedPlayers(teamId).collect { playersFlow ->
            if (sortName == SortType.ASCENDING) {
                sortName = SortType.DESCENDING
                playersList.value =
                    PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                        .sortedWith(compareBy({ it.full_name }))
            } else {
                sortName = SortType.ASCENDING
                playersList.value =
                    PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                        .sortedWith(compareByDescending({ it.full_name }))
            }
        }
    }

    suspend fun sortByName(teamId: Int) {
        repository.getSavedPlayers(teamId).collect { playersFlow ->
            if (sortName == SortType.ASCENDING) {
                playersList.value =
                    PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                        .sortedWith(compareBy({ it.full_name }))
            } else {
                playersList.value =
                    PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                        .sortedWith(compareByDescending({ it.full_name }))
            }
        }
    }

    suspend fun sortByPosition(teamId: Int) {
        repository.getSavedPlayers(teamId).collect { playersFlow ->
            if (sortPosition == SortType.ASCENDING) {
                sortPosition = SortType.DESCENDING
                playersList.value =
                    PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                        .sortedWith(compareBy({ it.position }))
            } else {
                sortPosition = SortType.ASCENDING
                playersList.value =  PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                    .sortedWith(compareByDescending({ it.position }))
            }
        }
    }

    suspend fun sortByNumber(teamId: Int) {
        repository.getSavedPlayers(teamId).collect { playersFlow ->
            if (sortNumber == SortType.ASCENDING) {
                sortNumber = SortType.DESCENDING
                playersList.value =
                    PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                        .sortedWith(compareBy({ convertToInt(it.number) }))
            } else {
                sortNumber = SortType.ASCENDING
                playersList.value =
                    PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                        .sortedWith(compareByDescending({ convertToInt(it.number) }))
            }
        }
    }

//    suspend private fun getTeamPlayersFromApi(teamId: Int): ResultState? {
//        return try {
//            val response = repository.getPlayers(teamId)
//            if (response.isSuccessful) {
//                response.body()?.let { ResultState.Success(it) }
//            } else {
//                ResultState.Error(response.message())
//            }
//        } catch (error: IOException) {
//            error.message?.let { ResultState.Error(it) }
//        }
//    }

    suspend private fun savePlayersToDb(teamId: Int, players: List<Player>) {
        repository.savePlayers(PlayerEntityAdapter.playerListToPlayerEntityList(teamId, players))
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
        var sortName: SortType = SortType.ASCENDING
        var sortPosition: SortType = SortType.ASCENDING
        var sortNumber: SortType = SortType.ASCENDING
    }
}