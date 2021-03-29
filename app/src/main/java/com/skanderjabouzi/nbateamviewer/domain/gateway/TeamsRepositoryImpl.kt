package com.skanderjabouzi.nbateamviewer.domain.gateway

import android.content.Context
import com.skanderjabouzi.nbateamviewer.data.repository.db.PlayersDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.Model.PlayerModel
import com.skanderjabouzi.nbateamviewer.data.Model.TeamModel
import com.skanderjabouzi.nbateamviewer.domain.entity.Players
import com.skanderjabouzi.nbateamviewer.domain.entity.Teams
import com.skanderjabouzi.nbateamviewer.data.repository.net.Network
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import retrofit2.Response

class TeamsRepositoryImpl(val context: Context): TeamsRepository {

  var db = TeamDatabase.getInstance(context)
  var retrofitClient: RetrofitClient
  private val teamDao: TeamDao = db.teamDao()
  private val playersDao: PlayersDao = db.playersDao()

  init {
    retrofitClient = RetrofitClient(Network.getRetrofit(context))
  }


  override suspend fun getSavedTeams(): List<TeamModel> {
    return teamDao.getTeams()
  }

  override suspend fun getSavedPlayers(teamId: Int): List<PlayerModel> {
    return playersDao.getPlayers(teamId)
  }

  override suspend fun saveTeams(teams: List<TeamModel>) {
    for (team in teams) {
      teamDao.insert(team)
    }
  }

  override suspend fun savePlayers(players: List<PlayerModel>) {
    for (player in players) {
      playersDao.insert(player)
    }
  }

  override suspend fun getTeams(): Response<Teams> {
    return retrofitClient.getTeams()
  }

  override suspend fun getPlayers(teamId: Int): Response<Players> {
    return retrofitClient.getPlayers(teamId)
  }
}