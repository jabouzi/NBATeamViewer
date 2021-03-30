package com.skanderjabouzi.nbateamviewer.domain.gateway

import android.content.Context
import com.skanderjabouzi.nbateamviewer.data.repository.db.PlayersDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import com.skanderjabouzi.nbateamviewer.domain.model.Players
import com.skanderjabouzi.nbateamviewer.data.repository.net.Network
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import retrofit2.Response

class TeamPlayersRepository(val context: Context) {

  var db = TeamDatabase.getInstance(context)
  var retrofitClient: RetrofitClient = RetrofitClient(Network.getRetrofit(context))
  private val teamDao: TeamDao = db.teamDao()
  private val playersDao: PlayersDao = db.playersDao()

  suspend fun getSavedPlayers(teamId: Int): List<PlayerEntity> {
    return playersDao.getPlayers(teamId)
  }

  suspend fun savePlayers(players: List<PlayerEntity>) {
    for (player in players) {
      playersDao.insert(player)
    }
  }

  suspend fun getPlayers(teamId: Int): Response<Players> {
    return retrofitClient.getPlayers(teamId)
  }
}