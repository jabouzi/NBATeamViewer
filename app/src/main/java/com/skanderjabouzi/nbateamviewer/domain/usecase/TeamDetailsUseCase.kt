package com.skanderjabouzi.nbateamviewer.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.skanderjabouzi.nbateamviewer.data.model.TeamDetails
import com.skanderjabouzi.nbateamviewer.domain.gateway.TeamDetailsRepository
import retrofit2.Response
import java.io.IOException

class TeamDetailsUseCase(val repository: TeamDetailsRepository): UseCase() {
    val teamDetails = MutableLiveData<TeamDetails>()

    suspend fun getTeamDetails(id: Int) {
        repository.getSavedTeamDetails(id).let { detailsLiveData ->
            teamDetails.value =
                detailsLiveData.value?.let { it ->
                    TeamDetailsEntityAdapter
                        .teamDetailsEntityToTeamDetails(it)
                }
        } ?.run {
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

//    suspend private fun getTeamDetailsFromApi(id: Int): ResultState? {
//        return try {
//            val response = repository.getTeamsDetails(id)
//            if (response.isSuccessful) {
//                response.body()?.let { ResultState.Success(it) }
//            } else {
//                ResultState.Error(response.message())
//            }
//        } catch (error: IOException) {
//            error.message?.let { ResultState.Error(it) }
//        }
//    }

    suspend private fun saveTeamDetailsToDb(teamDetails: TeamDetails) {
        repository.saveTeamDetails(
            TeamDetailsEntityAdapter
                .teamDetailsToTeamDetailsEntity(teamDetails)
        )
    }
}