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
        var teamsDbList = emptyList<Team>()
        val dbFlow = repository.getSavedTeams().map { databaseList ->
            Log.e("#####2", "$teamsDbList")
            if (databaseList.isNotEmpty()) {
                teamsDbList = TeamEntityAdapter.teamEntityListToTeamList(databaseList)
                teamsDbList
            } else {
                emptyList()
            }
        }

        Log.e("#####22", "$teamsDbList")

        //var teamsList: List<Team> = emptyList()
//        repository.getSavedTeams().map { databaseList ->
//            if (databaseList.isNotEmpty()) {
//                TeamEntityAdapter.teamEntityListToTeamList(databaseList)
//            } else {
        if (teamsDbList.isNotEmpty()) {
            return dbFlow
        } else {
            return repository.getTeams().map { teamsResponse ->
                Log.e("####3", "$teamsResponse")
                val resultState = getRequestFromApi(teamsResponse)
                if (resultState != null) {
                    //getRequestFromApi(teamsResponse)?.let { resultState ->
                    when (resultState) {
                        is ResultState.Success -> {
                            val teams = (resultState.data as Teams).teams
                            if (teams != null) {
                                Log.e("####33", "$teams")
                                //teams?.let {
                                saveTeamsToDb(teams)
                                teams
                            } else emptyList<Team>()
                        }
                        else -> emptyList<Team>()
                    }
                } else {
                    emptyList<Team>()
                }
            }
        }
    }
//            }
//        } as Flow<List<Team>>


//            repository.getSavedTeams().map DB@{ databaseList ->
//                Log.e("####0", "${databaseList.isNotEmpty()}")
//                if (databaseList.isNotEmpty()) {
//                    teamsList = TeamEntityAdapter.teamEntityListToTeamList(databaseList)
//                }
//            }



//        repository.getSavedTeams().collect { databaseList ->
//            Log.e("####1", "$databaseList")
//            //if (databaseList.isNotEmpty()) {
//            TeamEntityAdapter.teamEntityToTeam
//                teamsList = TeamEntityAdapter.teamEntityListToTeamList(databaseList)
//                Log.e("####11", "$teamsList")
//            //}
//        }
//
////            else {
////                Log.e("####111", "$teamsList")
//                repository.getTeams().collect { TeamsResponse ->
//                    Log.e("####2", "$TeamsResponse")
//                    getRequestFromApi(TeamsResponse)?.let { resultState ->
//                        when (resultState) {
//                            is ResultState.Success -> {
//                                val teams = (resultState.data as Teams).teams
//                                Log.e("####3", "$teamsList")
//                                teams?.let {
//                                    saveTeamsToDb(it)
//                                    teamsList = it
//                                }
//                            }
//                            else -> teamsList = emptyList<Team>()
//                        }
//                    }
//                }
////            }
////        }


    suspend fun sortByName(): List<Team>? {
        var teamsList: List<Team>? = null
        repository.getSavedTeams().collect { teamsFlow ->
            if (sortName == SortType.ASCENDING) {
                teamsList =
                    teamsFlow?.let {
                        TeamEntityAdapter.teamEntityListToTeamList(it)
                            .sortedWith(compareBy { it.name })
                    }
            } else {
                teamsList =
                    teamsFlow?.let {
                        TeamEntityAdapter.teamEntityListToTeamList(it)
                            .sortedWith(compareByDescending { it.name })
                    }
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
                    teamsFlow?.let {
                        TeamEntityAdapter.teamEntityListToTeamList(it)
                            .sortedWith(compareBy { it.wins })
                    }
            } else {
                teamsList =
                    teamsFlow?.let {
                        TeamEntityAdapter.teamEntityListToTeamList(it)
                            .sortedWith(compareByDescending { it.wins })
                    }
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
                    teamsFlow?.let {
                        TeamEntityAdapter.teamEntityListToTeamList(it)
                            .sortedWith(compareBy { it.losses })
                    }
            } else {
                teamsList =
                    teamsFlow?.let {
                        TeamEntityAdapter.teamEntityListToTeamList(it)
                            .sortedWith(compareByDescending { it.losses })
                    }
            }
            sortLosses = getSortBy(sortLosses)
        }
        return teamsList
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