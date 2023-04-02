package com.skanderjabouzi.nbateamviewer.data.repository.gateway

import android.content.Context
import com.skanderjabouzi.nbateamviewer.data.repository.db.PlayersDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.Players
import com.skanderjabouzi.nbateamviewer.data.repository.net.Network
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class TeamPlayersRepository @Inject constructor(
  private val retrofitClient: RetrofitClient,
  private val playersDao: PlayersDao
) {

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