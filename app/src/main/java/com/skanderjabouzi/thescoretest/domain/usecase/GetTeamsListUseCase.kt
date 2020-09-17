package com.skanderjabouzi.thescoretest.domain.listener.usecase

import android.util.Log
import com.skanderjabouzi.thescoretest.data.model.net.Player
import com.skanderjabouzi.thescoretest.data.model.net.Team
import com.skanderjabouzi.thescoretest.data.net.TeamsRepository
import com.skanderjabouzi.thescoretest.domain.usecase.TeamEntityConverter
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GetTeamsListUseCase @Inject constructor(val repository: TeamsRepository): CoroutineScope {

    private val parentJob: Job = SupervisorJob()
    
    suspend fun getTeams(): List<Team> {
        var teams: List<Team> = listOf()
        withContext(Dispatchers.IO) {
            teams = getTeamsFromDb()
            if (teams.isNullOrEmpty()) {
                teams = getTeamsFromApi()
                saveTeamsToDb(teams)
            }
        }
        return teams
    }

    suspend private fun getTeamsFromApi(): List<Team> {
        var teams: List<Team> = listOf()
        teams = repository.getTeams()
        return teams
    }

    suspend private fun getTeamsFromDb(): List<Team> {
        var Teams: List<Team> = listOf()
        Teams = TeamEntityConverter.teamEntityListToTeamList(repository.getSavedTeams())
        return Teams
    }

    suspend private fun saveTeamsToDb(teams: List<Team>) {
        repository.saveTeams(TeamEntityConverter.teamListToTeamEntityList(teams))
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob
}