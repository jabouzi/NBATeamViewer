package com.skanderjabouzi.nbateamviewer.domain.net

import android.content.Context
import com.skanderjabouzi.nbateamviewer.data.db.PlayersDao
import com.skanderjabouzi.nbateamviewer.data.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.model.db.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.db.TeamEntity
import com.skanderjabouzi.nbateamviewer.data.model.net.Player
import com.skanderjabouzi.nbateamviewer.data.model.net.Players
import com.skanderjabouzi.nbateamviewer.data.model.net.Team
import com.skanderjabouzi.nbateamviewer.data.model.net.Teams
import com.skanderjabouzi.nbateamviewer.data.net.NBAResult
import com.skanderjabouzi.nbateamviewer.data.net.Network
import com.skanderjabouzi.nbateamviewer.data.net.RetrofitClient

class TeamsRepositoryImpl(val context: Context): TeamsRepository {

  var db = TeamDatabase.getInstance(context)
  var retrofitClient: RetrofitClient
  private val teamDao: TeamDao = db.teamDao()
  private val playersDao: PlayersDao = db.playersDao()

  init {
    retrofitClient = RetrofitClient(Network.getRetrofit(context))
  }


  override suspend fun getSavedTeams(): List<TeamEntity> {
    return teamDao.getTeams()
  }

  override suspend fun getSavedPlayers(teamId: Int): List<PlayerEntity> {
    return playersDao.getPlayers(teamId)
  }

  override suspend fun saveTeams(teams: List<TeamEntity>) {
    for (team in teams) {
      teamDao.insert(team)
    }
  }

  override suspend fun savePlayers(players: List<PlayerEntity>) {
    for (player in players) {
      playersDao.insert(player)
    }
  }

  override suspend fun getTeams(): List<Team> {
    return retrofitClient.getTeams().teams
  }

  override suspend fun getPlayers(teamId: Int): List<Player> {
    return retrofitClient.getPlayers(teamId).players
  }
}