package com.skanderjabouzi.nbateamviewer.data.repository.gateway

import android.content.Context
import android.util.Log
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
    val temp = teamDao.getTeams()
    Log.e("####","$temp")
    return temp
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