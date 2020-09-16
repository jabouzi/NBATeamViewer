package com.skanderjabouzi.thescoretest.data.repo

import com.skanderjabouzi.thescoretest.core.TheScoreApp
import com.skanderjabouzi.thescoretest.data.db.PlayersDao
import com.skanderjabouzi.thescoretest.data.db.TeamDao
import com.skanderjabouzi.thescoretest.data.model.db.PlayerEntity
import com.skanderjabouzi.thescoretest.data.model.db.TeamEntity
import com.skanderjabouzi.thescoretest.data.model.net.Player
import com.skanderjabouzi.thescoretest.data.model.net.Players
import com.skanderjabouzi.thescoretest.data.model.net.Team
import com.skanderjabouzi.thescoretest.data.net.RetrofitClient
import com.skanderjabouzi.thescoretest.data.net.TeamsRepository
import javax.inject.Inject

class TeamsRepositoryImpl @Inject constructor() : TeamsRepository {

  @Inject lateinit var retrofitClient: RetrofitClient
  private val teamDao: TeamDao = TheScoreApp.INSTANCE.db.movieDao()
  private val playersDao: PlayersDao = TheScoreApp.INSTANCE.db.playerDao()

  init {
    retrofitClient = TheScoreApp.INSTANCE.appComponent.getRetrofitClient()
  }

  override suspend fun getSavedTeams(): List<TeamEntity> {
    return teamDao.getTeams()
  }

  override suspend fun getSavedPlayers(teamId: Int): List<PlayerEntity> {
    return playersDao.getPlayers(teamId)
  }

  override suspend fun saveTeam(team: TeamEntity) {
    teamDao.insert(team)
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