package com.skanderjabouzi.thescoretest.data.net

import com.skanderjabouzi.thescoretest.data.model.net.Players
import com.skanderjabouzi.thescoretest.data.model.net.Teams
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamsApi {

  @GET("api/teams")
  suspend fun getTeams(): Teams

  @GET("api/{team_id}")
  suspend fun getPlayers(@Path("team_id") teamId: Int): Players
}