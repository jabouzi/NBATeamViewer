package com.skanderjabouzi.nbateamviewer.data.repository.net

import com.skanderjabouzi.nbateamviewer.data.model.Players
import com.skanderjabouzi.nbateamviewer.data.model.TeamDetails
import com.skanderjabouzi.nbateamviewer.data.model.Teams
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitClient (val retrofit: Retrofit) {

  private val teamsApi: TeamsApi

  init {
    teamsApi = retrofit.create(TeamsApi::class.java)
  }

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