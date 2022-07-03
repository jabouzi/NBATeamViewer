package com.skanderjabouzi.nbateamviewer.domain.usecase

import android.util.Log
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.data.repository.gateway.TeamsRepository
import com.skanderjabouzi.nbateamviewer.data.model.Teams
import com.skanderjabouzi.nbateamviewer.domain.helpers.ResultState
import com.skanderjabouzi.nbateamviewer.domain.helpers.SortType
import com.skanderjabouzi.nbateamviewer.domain.helpers.TeamEntityAdapter
import com.skanderjabouzi.nbateamviewer.domain.helpers.UseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ViewModelScoped
class TeamsListUseCase @Inject constructor(val repository: TeamsRepository): UseCase() {

    suspend fun getTeams(): Flow<List<Team>> {
        var teamsList: List<Team>? = null
        //repository.getSavedTeams().collect { teamsFlow ->
            if (repository.getSavedTeams() != null) {
                teamsList = TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
            } else {
                getRequestFromApi(repository.getTeams())?.let {
                    when (it) {
                        is ResultState.Success -> {
                            val teams = (it.data as Teams).teams
                            teamsList = teams
                            Log.e("####2", "${teamsList}")
                            teams?.let {
                                saveTeamsToDb(it)
                                teamsList = it
                            }
                        }
                        else -> error.postValue((it as ResultState.Error).error)
                    }
                }
            }
        }
        Log.e("####22", "${teamsList}")
        return teamsList
    }

    suspend fun sortByName(): List<Team>? {
        var teamsList: List<Team>? = null
        repository.getSavedTeams().collect { teamsFlow ->
            if (sortName == SortType.ASCENDING) {
                teamsList =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareBy({ it.name }))
            } else {
                teamsList =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareByDescending({ it.name }))
            }
            sortName = getSortBy(sortName)
        }
        return teamsList
    }

    suspend fun sortByWins(): List<Team>? {
        var teamsList: List<Team>? = null
        repository.getSavedTeams().collect { teamsFlow ->
            if (sortWins == SortType.ASCENDING) {
                teamsList =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareBy({ it.wins }))
            } else {
                teamsList =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareByDescending({ it.wins }))
            }
            sortWins = getSortBy(sortWins)
        }
        return teamsList
    }

    suspend fun sortByLosses(): List<Team>? {
        var teamsList: List<Team>? = null
        repository.getSavedTeams().collect { teamsFlow ->
            if (sortLosses == SortType.ASCENDING) {
                teamsList =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareBy({ it.losses }))
            } else {
                teamsList =
                    TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                        .sortedWith(compareByDescending({ it.losses }))
            }
            sortLosses = getSortBy(sortLosses)
        }
        return teamsList
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