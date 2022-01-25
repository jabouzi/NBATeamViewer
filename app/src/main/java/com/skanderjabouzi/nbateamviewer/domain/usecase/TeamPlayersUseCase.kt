package com.skanderjabouzi.nbateamviewer.domain.usecase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.skanderjabouzi.nbateamviewer.data.repository.gateway.TeamPlayersRepository
import com.skanderjabouzi.nbateamviewer.data.model.Player
import com.skanderjabouzi.nbateamviewer.data.model.Players
import com.skanderjabouzi.nbateamviewer.domain.helpers.PlayerEntityAdapter
import com.skanderjabouzi.nbateamviewer.domain.helpers.ResultState
import com.skanderjabouzi.nbateamviewer.domain.helpers.SortType
import com.skanderjabouzi.nbateamviewer.domain.helpers.UseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ViewModelScoped
class TeamPlayersUseCase @Inject constructor(val repository: TeamPlayersRepository): UseCase() {
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
                            players?.let {
                                it1 -> savePlayersToDb(teamId, it1)
                                playersList.postValue(it1)
                            }

                        }
                        else -> error.postValue((it as ResultState.Error).error)
                    }
                }
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
            sortName = getSortBy(sortName)
        }
    }

    suspend fun sortByPosition(teamId: Int) {
        repository.getSavedPlayers(teamId).collect { playersFlow ->
            if (sortPosition == SortType.ASCENDING) {
                playersList.value =
                    PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                        .sortedWith(compareBy({ it.position }))
            } else {
                playersList.value =  PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                    .sortedWith(compareByDescending({ it.position }))
            }
            sortPosition = getSortBy(sortPosition)
        }
    }

    suspend fun sortByNumber(teamId: Int) {
        repository.getSavedPlayers(teamId).collect { playersFlow ->
            if (sortNumber == SortType.ASCENDING) {
                playersList.value =
                    PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                        .sortedWith(compareBy({ it.number?.let { it1 -> convertToInt(it1) } }))
            } else {
                playersList.value =
                    PlayerEntityAdapter.playerEntityListToPlayerList(playersFlow)
                        .sortedWith(compareByDescending({ it.number?.let { it1 -> convertToInt(it1) } }))
            }
            sortNumber = getSortBy(sortNumber)
        }
    }

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