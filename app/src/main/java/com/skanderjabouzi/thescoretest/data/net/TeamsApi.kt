package com.skanderjabouzi.thescoretest.data.net

import com.skanderjabouzi.thescoretest.data.model.net.Players
import com.skanderjabouzi.thescoretest.data.model.net.Teams
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamsApi {

  @GET("teams.json")
  suspend fun getTeams(): Teams

  @GET("{team_id}.json")
  suspend fun getPlayers(@Path("team_id") teamId: Int): Players
}