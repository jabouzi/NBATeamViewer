package com.skanderjabouzi.nbateamviewer.data.net

import com.skanderjabouzi.nbateamviewer.data.model.net.Players
import com.skanderjabouzi.nbateamviewer.data.model.net.Teams
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

}