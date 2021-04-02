package com.skanderjabouzi.nbateamviewer.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.skanderjabouzi.nbateamviewer.data.model.TeamDetails
import com.skanderjabouzi.nbateamviewer.domain.gateway.TeamDetailsRepository

class TeamDetailsUseCase(val repository: TeamDetailsRepository) : UseCase() {
    val teamDetails = MutableLiveData<TeamDetails>()

    suspend fun getTeamDetails(id: Int) {
        repository.getSavedTeamDetails(id).value.let { detailsLiveData ->
            if (detailsLiveData != null) {
                teamDetails.postValue(
                    TeamDetailsEntityAdapter
                        .teamDetailsEntityToTeamDetails(detailsLiveData)
                )
            } else {
                getRequestFromApi(repository.getTeamsDetails(id))?.let {
                    when (it) {
                        is ResultState.Success -> {
                            val details = (it.data as TeamDetails)
                            saveTeamDetailsToDb(details)
                            teamDetails.postValue(details)
                        }
                        else -> error.postValue((it as ResultState.Error).error)
                    }
                }
            }
        }
    }

    suspend private fun saveTeamDetailsToDb(teamDetails: TeamDetails) {
        repository.saveTeamDetails(
            TeamDetailsEntityAdapter
                .teamDetailsToTeamDetailsEntity(teamDetails)
        )
    }
}