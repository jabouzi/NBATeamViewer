package com.skanderjabouzi.nbateamviewer.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.domain.gateway.TeamsRepository
import com.skanderjabouzi.nbateamviewer.data.model.Teams
import kotlinx.coroutines.flow.collect
import java.io.IOException

class TeamsListUseCase (val repository: TeamsRepository): UseCase() {
    val teamsList = MutableLiveData<List<Team>>()

    suspend fun getTeams() {
        repository.getSavedTeams().collect { teamsFlow ->
            if (!teamsFlow.isNullOrEmpty()) {
                teamsList.value = TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
            } else {
                getRequestFromApi(repository.getTeams())?.let {
                    when (it) {
                        is ResultState.Success -> {
                            val teams = (it.data as Teams).teams
                            saveTeamsToDb(teams)
                            teamsList.postValue(teams)
                        }
                        else -> error.postValue((it as ResultState.Error).error)
                    }
                }
            }
        }
    }

    suspend fun sortByName() {
        repository.getSavedTeams().collect { teamsFlow ->
            if (sortName == SortType.ASCENDING) {
                sortName = SortType.DESCENDING
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareBy({ it.name }))
            } else {
                sortName = SortType.ASCENDING
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareByDescending({ it.name }))
            }
        }
    }

    suspend fun sortByWins() {
        repository.getSavedTeams().collect { teamsFlow ->
            if (sortWins == SortType.ASCENDING) {
                sortWins = SortType.DESCENDING
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareBy({ it.wins }))
            } else {
                sortWins = SortType.ASCENDING
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareByDescending({ it.wins }))
            }
        }
    }

    suspend fun sortByLosses() {
        repository.getSavedTeams().collect { teamsFlow ->
            if (sortLosses == SortType.ASCENDING) {
                sortLosses = SortType.DESCENDING
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareBy({ it.losses }))
            } else {
                sortLosses = SortType.ASCENDING
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareByDescending({ it.losses }))
            }
        }
    }

//    suspend private fun getTeamsFromApi(): ResultState? {
//        return try {
//            val response = repository.getTeams()
//            if (response.isSuccessful) {
//                response.body()?.let { ResultState.Success(it) }
//            } else {
//                ResultState.Error(response.message())
//            }
//        } catch (error: IOException) {
//            error.message?.let { ResultState.Error(it) }
//        }
//    }

    suspend private fun saveTeamsToDb(teams: List<Team>) {
        repository.saveTeams(TeamEntityAdapter.teamListToTeamEntityList(teams))
    }

    companion object {
        var sortName: SortType = SortType.ASCENDING
        var sortWins: SortType = SortType.ASCENDING
        var sortLosses: SortType = SortType.ASCENDING
    }
}