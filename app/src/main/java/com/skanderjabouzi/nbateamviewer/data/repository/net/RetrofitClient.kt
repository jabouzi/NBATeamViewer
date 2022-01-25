package com.skanderjabouzi.nbateamviewer.data.repository.net

import com.skanderjabouzi.nbateamviewer.data.model.Players
import com.skanderjabouzi.nbateamviewer.data.model.TeamDetails
import com.skanderjabouzi.nbateamviewer.data.model.Teams
import retrofit2.Response
import javax.inject.Inject

class RetrofitClient @Inject constructor(val teamsApi: TeamsApi) {

  suspend fun getTeams(): Response<Teams> {
    return teamsApi.getTeams()
  }

  suspend fun getPlayers(teamId: Int): Response<Players> {
    return teamsApi.getPlayers(teamId)
  }

  suspend fun getTeamDetails(teamId: Int): Response<TeamDetails> {
    return teamsApi.getTeamDetails(teamId)
  }

}