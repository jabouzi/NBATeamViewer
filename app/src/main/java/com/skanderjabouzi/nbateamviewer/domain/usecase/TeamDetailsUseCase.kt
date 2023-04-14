package com.skanderjabouzi.nbateamviewer.domain.usecase

import com.skanderjabouzi.nbateamviewer.data.model.TeamDetails
import com.skanderjabouzi.nbateamviewer.data.repository.gateway.TeamDetailsRepository
import com.skanderjabouzi.nbateamviewer.domain.helpers.ResultState
import com.skanderjabouzi.nbateamviewer.domain.helpers.TeamDetailsEntityAdapter
import com.skanderjabouzi.nbateamviewer.domain.helpers.UseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class TeamDetailsUseCase @Inject constructor(
    private val repository: TeamDetailsRepository
) : UseCase() {

//    suspend fun getTeamDetails(id: Int): Flow<TeamDetails> {
//        var teamDetailResult: TeamDetails? = null
//        repository.getSavedTeamDetails(id).value.let { detailsLiveData ->
//            if (detailsLiveData != null) {
//                teamDetailResult = TeamDetailsEntityAdapter
//                    .teamDetailsEntityToTeamDetails(detailsLiveData)
////                teamDetails.postValue(
////                    TeamDetailsEntityAdapter
////                        .teamDetailsEntityToTeamDetails(detailsLiveData)
////                )
//            } else {
//                getRequestFromApi(repository.getTeamsDetails(id))?.let {
//                    when (it) {
//                        is ResultState.Success -> {
//                            val details = (it.data as TeamDetails)
//                            saveTeamDetailsToDb(details)
//                            teamDetailResult = details
//                        }
//                        else -> error.postValue((it as ResultState.Error).error)
//                    }
//                }
//            }
//        }
//        return teamDetailResult
//    }

    private suspend fun getLocalTeamDetails(id: Int) : Flow<TeamDetails> {
        return repository.getSavedTeamDetails(id).map { teamDetails ->
            TeamDetailsEntityAdapter.teamDetailsEntityToTeamDetails(teamDetails)
        }
    }

    private suspend fun getRemoteTeamDetails(id: Int): Flow<TeamDetails> {
        val r = repository.getTeamsDetails(id).map { response ->
            val resultState = getRequestFromApi(response)
            if (resultState != null) {
                when (resultState) {
                    is ResultState.Success -> {
                        val details = (resultState.data as TeamDetails)
                        saveTeamDetailsToDb(details)
                        details
                    }
                    else -> emptyList<TeamDetails>()
                }
            } else {
                emptyList<TeamDetails>()
            }
        }

        return r as Flow<TeamDetails>
    }

    suspend private fun saveTeamDetailsToDb(teamDetails: TeamDetails) {
        repository.saveTeamDetails(
            TeamDetailsEntityAdapter
                .teamDetailsToTeamDetailsEntity(teamDetails)
        )
    }
}