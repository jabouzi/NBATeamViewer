package com.skanderjabouzi.nbateamviewer.domain.listener.usecase

import android.util.Log
import com.skanderjabouzi.nbateamviewer.data.model.net.Team
import com.skanderjabouzi.nbateamviewer.domain.net.TeamsRepository
import com.skanderjabouzi.nbateamviewer.domain.usecase.SortType
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamEntityConverter
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class GetTeamsListUseCase (val repository: TeamsRepository) {

    suspend fun getTeams(): List<Team> {
        var teams: List<Team> = listOf()
        Log.e("## GetTeamsListUseCase", "${Thread.currentThread().name}")
        Log.e("## GetTeamsListUseCase", "${coroutineContext}")
        teams = getTeamsFromDb()
        if (teams.isNullOrEmpty()) {
            teams = getTeamsFromApi()
            saveTeamsToDb(teams)
        }
        return teams
    }

    suspend fun sortByName(): List<Team> {
        var teams: List<Team> = listOf()
        withContext(Dispatchers.Default) {
            Log.e("## sortByName", "${Thread.currentThread().name}")
            Log.e("## sortByName", "${coroutineContext}")
            teams = getTeams()
            if (sortByName == SortType.ASCENDING) {
                sortByName = SortType.DESCENDING
                teams = teams.sortedWith(compareBy({ it.name }))
            } else {
                sortByName = SortType.ASCENDING
                teams = teams.sortedWith(compareByDescending({ it.name }))
            }
        }
        return teams
    }

    suspend fun sortByWins(): List<Team> {
        val teams = getTeams()
        if (sortByWins == SortType.ASCENDING) {
            sortByWins = SortType.DESCENDING
            return teams.sortedWith(compareBy({ it.wins }))
        } else {
            sortByWins = SortType.ASCENDING
            return teams.sortedWith(compareByDescending({ it.wins }))
        }
    }

    suspend fun sortByLosses(): List<Team> {
        val teams = getTeams()
        if (sortByLosses == SortType.ASCENDING) {
            sortByLosses = SortType.DESCENDING
            return teams.sortedWith(compareBy({ it.losses }))
        } else {
            sortByLosses = SortType.ASCENDING
            return teams.sortedWith(compareByDescending({ it.losses }))
        }
    }

    suspend fun getTeamsFromApi(): List<Team> {
        var teams: List<Team> = listOf()
        teams = repository.getTeams()
        return teams
    }

    suspend fun getTeamsFromDb(): List<Team> {
        var Teams: List<Team> = listOf()
        Teams = TeamEntityConverter.teamEntityListToTeamList(repository.getSavedTeams())
        return Teams
    }

    suspend private fun saveTeamsToDb(teams: List<Team>) {
        repository.saveTeams(TeamEntityConverter.teamListToTeamEntityList(teams))
    }

    companion object {
        var sortByName: SortType = SortType.ASCENDING
        var sortByWins: SortType = SortType.ASCENDING
        var sortByLosses: SortType = SortType.ASCENDING
    }
}