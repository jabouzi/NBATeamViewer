package com.skanderjabouzi.thescoretest.data.repo

import androidx.lifecycle.LiveData
import com.skanderjabouzi.thescoretest.core.TheScoreApp
import com.skanderjabouzi.thescoretest.data.db.TeamDao
import com.skanderjabouzi.thescoretest.data.model.Player
import com.skanderjabouzi.thescoretest.data.model.Players
import com.skanderjabouzi.thescoretest.data.model.Team
import com.skanderjabouzi.thescoretest.data.net.RetrofitClient
import com.skanderjabouzi.thescoretest.data.net.TeamsRepository
import javax.inject.Inject

class TeamsRepositoryImpl @Inject constructor() : TeamsRepository {

  @Inject lateinit var retrofitClient: RetrofitClient
  private val teamDao: TeamDao = TheScoreApp.INSTANCE.db.movieDao()

  init {
    retrofitClient = TheScoreApp.INSTANCE.appComponent.getRetrofitClient()
  }

  override fun getSavedTeams(): LiveData<List<Team>> {
    return teamDao.getTeams()
  }

  override fun getPlayers(teamId: Int): LiveData<List<Player>> {
    return teamDao.getPlayers(teamId)
  }

  override fun saveTeam(team: Team) {
    teamDao.insert(team)
  }

  override fun savePlayers(player: Players) {
    teamDao.insert(player)
  }
}