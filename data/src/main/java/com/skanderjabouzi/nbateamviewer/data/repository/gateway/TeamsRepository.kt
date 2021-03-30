package com.skanderjabouzi.nbateamviewer.domain.gateway

import android.content.Context
import com.skanderjabouzi.nbateamviewer.data.repository.db.PlayersDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity
import com.skanderjabouzi.nbateamviewer.domain.model.Players
import com.skanderjabouzi.nbateamviewer.domain.model.Teams
import com.skanderjabouzi.nbateamviewer.data.repository.net.Network
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import retrofit2.Response

class TeamsRepository(val context: Context) {

  var db = TeamDatabase.getInstance(context)
  var retrofitClient: RetrofitClient
  private val teamDao: TeamDao = db.teamDao()
  private val playersDao: PlayersDao = db.playersDao()

  init {
    retrofitClient = RetrofitClient(Network.getRetrofit(context))
  }


  suspend fun getSavedTeams(): List<TeamEntity> {
    return teamDao.getTeams()
  }

  suspend fun saveTeams(teams: List<TeamEntity>) {
    for (team in teams) {
      teamDao.insert(team)
    }
  }

  suspend fun getTeams(): Response<Teams> {
    return retrofitClient.getTeams()
  }
}