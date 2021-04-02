package com.skanderjabouzi.nbateamviewer.domain.gateway

import android.content.Context
import com.skanderjabouzi.nbateamviewer.data.repository.db.PlayersDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.Players
import com.skanderjabouzi.nbateamviewer.data.repository.net.Network
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class TeamPlayersRepository(val context: Context) {

  var db = TeamDatabase.getInstance(context)
  var retrofitClient: RetrofitClient = RetrofitClient(Network.getRetrofit(context))
  private val playersDao: PlayersDao = db.playersDao()

  fun getSavedPlayers(teamId: Int): Flow<List<PlayerEntity>> {
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