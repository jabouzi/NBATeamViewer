package com.skanderjabouzi.nbateamviewer.domain.net

import com.skanderjabouzi.nbateamviewer.core.App
import com.skanderjabouzi.nbateamviewer.data.db.PlayersDao
import com.skanderjabouzi.nbateamviewer.data.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.model.db.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.db.TeamEntity
import com.skanderjabouzi.nbateamviewer.data.model.net.Player
import com.skanderjabouzi.nbateamviewer.data.model.net.Team
import com.skanderjabouzi.nbateamviewer.data.net.RetrofitClient

class TeamsRepositoryImpl: TeamsRepository {

  lateinit var retrofitClient: RetrofitClient
  private val teamDao: TeamDao = App.INSTANCE.db.teamDao()
  private val playersDao: PlayersDao = App.INSTANCE.db.playersDao()

  init {
    //retrofitClient = RetrofitClient()
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