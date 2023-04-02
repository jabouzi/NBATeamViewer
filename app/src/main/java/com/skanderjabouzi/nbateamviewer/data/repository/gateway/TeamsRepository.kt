package com.skanderjabouzi.nbateamviewer.data.repository.gateway

import android.content.Context
import android.util.Log
import androidx.compose.samples.crane.di.DefaultDispatcher
import androidx.compose.samples.crane.di.IODispatcher
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity
import com.skanderjabouzi.nbateamviewer.data.model.Teams
import com.skanderjabouzi.nbateamviewer.data.repository.net.Network
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class TeamsRepository @Inject constructor(
  private val retrofitClient: RetrofitClient,
  private val teamDao: TeamDao,
  @IODispatcher private val dispatcher: CoroutineDispatcher,
) {



  fun getSavedTeams(): Flow<List<TeamEntity>> {
    val temp = teamDao.getTeams()
    Log.e("####","$temp")
    return temp
  }

  suspend fun saveTeams(teams: List<TeamEntity>) {
    withContext(dispatcher) {
      for (team in teams) {
        teamDao.insert(team)
      }
    }
  }

  suspend fun getTeams(): Flow<Response<Teams>> {
    val teams = retrofitClient.getTeams()
    Log.e("####1","${teams.body()}")
    return flow {
      emit(teams)
    }
  }
}