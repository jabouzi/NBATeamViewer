package com.skanderjabouzi.nbateamviewer.domain.gateway

import android.content.Context
import androidx.lifecycle.LiveData
import com.skanderjabouzi.nbateamviewer.data.entity.TeamDetailsEntity
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity
import com.skanderjabouzi.nbateamviewer.data.model.Teams
import com.skanderjabouzi.nbateamviewer.data.repository.net.Network
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class TeamsRepository(val context: Context) {

  var db = TeamDatabase.getInstance(context)
  var retrofitClient: RetrofitClient = RetrofitClient(Network.getRetrofit(context))
  private val teamDao: TeamDao = db.teamDao()

  fun getSavedTeams(): Flow<List<TeamEntity>> {
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