package com.skanderjabouzi.nbateamviewer.data.repository.net

import com.skanderjabouzi.nbateamviewer.domain.model.Players
import com.skanderjabouzi.nbateamviewer.domain.model.Teams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamsApi {

  @GET("teams.json")
  suspend fun getTeams(): Response<Teams>

  @GET("{team_id}.json")
  suspend fun getPlayers(@Path("team_id") teamId: Int): Response<Players>
}