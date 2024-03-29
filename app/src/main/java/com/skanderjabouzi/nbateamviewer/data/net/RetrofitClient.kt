package com.skanderjabouzi.nbateamviewer.data.net

import android.util.Log
import com.skanderjabouzi.nbateamviewer.data.model.net.Players
import com.skanderjabouzi.nbateamviewer.data.model.net.Teams
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitClient @Inject constructor() {

  @Inject lateinit var teamsApi: TeamsApi

//  init {
//    teamsApi = retrofit.create(TeamsApi::class.java)
//  }

  suspend fun getTeams(): Teams {
    return teamsApi.getTeams()
  }

  suspend fun getPlayers(teamId: Int): Players {
    return teamsApi.getPlayers(teamId)
  }

}