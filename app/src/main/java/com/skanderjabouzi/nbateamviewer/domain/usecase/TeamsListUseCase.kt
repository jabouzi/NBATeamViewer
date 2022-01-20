package com.skanderjabouzi.nbateamviewer.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.data.repository.gateway.TeamsRepository
import com.skanderjabouzi.nbateamviewer.data.model.Teams
import com.skanderjabouzi.nbateamviewer.domain.helpers.ResultState
import com.skanderjabouzi.nbateamviewer.domain.helpers.SortType
import com.skanderjabouzi.nbateamviewer.domain.helpers.TeamEntityAdapter
import com.skanderjabouzi.nbateamviewer.domain.helpers.UseCase
import kotlinx.coroutines.flow.collect

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
                            teams?.let {
                                saveTeamsToDb(it)
                                teamsList.postValue(it)
                            }
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
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareBy({ it.name }))
            } else {
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareByDescending({ it.name }))
            }
            sortName = getSortBy(sortName)
        }
    }

    suspend fun sortByWins() {
        repository.getSavedTeams().collect { teamsFlow ->
            if (sortWins == SortType.ASCENDING) {
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareBy({ it.wins }))
            } else {
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareByDescending({ it.wins }))
            }
            sortWins = getSortBy(sortWins)
        }
    }

    suspend fun sortByLosses() {
        repository.getSavedTeams().collect { teamsFlow ->
            if (sortLosses == SortType.ASCENDING) {
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareBy({ it.losses }))
            } else {
                teamsList.value =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareByDescending({ it.losses }))
            }
            sortLosses = getSortBy(sortLosses)
        }
    }

    suspend private fun saveTeamsToDb(teams: List<Team>) {
        repository.saveTeams(TeamEntityAdapter.teamListToTeamEntityList(teams))
    }

    companion object {
        var sortName: SortType = SortType.ASCENDING
        var sortWins: SortType = SortType.ASCENDING
        var sortLosses: SortType = SortType.ASCENDING
    }
}