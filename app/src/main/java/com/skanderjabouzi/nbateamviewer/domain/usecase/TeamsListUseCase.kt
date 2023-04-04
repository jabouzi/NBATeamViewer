package com.skanderjabouzi.nbateamviewer.domain.usecase

import android.util.Log
import androidx.compose.samples.crane.di.DefaultDispatcher
import androidx.compose.samples.crane.di.DispatchersModule
import androidx.compose.samples.crane.di.IODispatcher
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.data.repository.gateway.TeamsRepository
import com.skanderjabouzi.nbateamviewer.data.model.Teams
import com.skanderjabouzi.nbateamviewer.domain.helpers.ResultState
import com.skanderjabouzi.nbateamviewer.domain.helpers.SortType
import com.skanderjabouzi.nbateamviewer.domain.helpers.TeamEntityAdapter
import com.skanderjabouzi.nbateamviewer.domain.helpers.UseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class TeamsListUseCase @Inject constructor(
    private val repository: TeamsRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
): UseCase() {

    suspend fun getTeams(): Flow<List<Team>> {
        val teamsCount = repository.getTeamsCount()
        Log.e("# TeamsListUseCase 1", "$teamsCount")
        return if (teamsCount > 0) {
            getLocalTeams()
        } else {
            getRemoteTeams()
        }
    }

    private suspend fun getLocalTeams(): Flow<List<Team>> {
        return repository.getSavedTeams().map { databaseList ->
            Log.e("# TeamsListUseCase 2", "$databaseList")
            TeamEntityAdapter.teamEntityListToTeamList(databaseList)
        }
    }

    private suspend fun getRemoteTeams(): Flow<List<Team>> {
        return repository.getTeams().map { teamsResponse ->
            Log.e("# TeamsListUseCase 3", "$teamsResponse")
            val resultState = getRequestFromApi(teamsResponse)
            if (resultState != null) {
                when (resultState) {
                    is ResultState.Success -> {
                        val teams = (resultState.data as Teams).teams
                        if (teams != null) {
                            Log.e("# TeamsListUseCase 4", "$teams")
                            //teams?.let {
                            saveTeamsToDb(teams)
                            teams
                        } else emptyList()
                    }
                    else -> emptyList()
                }
            } else {
                emptyList()
            }
        }
    }

    suspend fun sortByName(): Flow<List<Team>> {
        sortName = getSortBy(sortName)
        return repository.getSavedTeams().map { teamsFlow ->
            if (sortName == SortType.ASCENDING) {
                TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                    .sortedWith(compareBy { it.name })
            } else {
                TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                    .sortedWith(compareByDescending { it.name })
            }
        }
    }

    suspend fun sortByWins(): Flow<List<Team>> {
        sortWins = getSortBy(sortWins)
        return repository.getSavedTeams().map { teamsFlow ->
            if (sortWins == SortType.ASCENDING) {
                TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                    .sortedWith(compareBy { it.wins })
            } else {
                TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                    .sortedWith(compareByDescending { it.wins })
            }
        }
    }

    suspend fun sortByLosses(): Flow<List<Team>> {
        sortLosses = getSortBy(sortLosses)
        return repository.getSavedTeams().map { teamsFlow ->
            if (sortLosses == SortType.ASCENDING) {
                TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                    .sortedWith(compareBy { it.losses })
            } else {
                TeamEntityAdapter.teamEntityListToTeamList(teamsFlow)
                    .sortedWith(compareByDescending { it.losses })
            }
        }
    }

    private suspend fun saveTeamsToDb(teams: List<Team>) {
        repository.saveTeams(TeamEntityAdapter.teamListToTeamEntityList(teams))
    }

    companion object {
        var sortName: SortType = SortType.ASCENDING
        var sortWins: SortType = SortType.ASCENDING
        var sortLosses: SortType = SortType.ASCENDING
    }
}