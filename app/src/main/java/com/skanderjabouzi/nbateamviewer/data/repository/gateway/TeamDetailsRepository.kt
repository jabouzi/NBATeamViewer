package com.skanderjabouzi.nbateamviewer.data.repository.gateway

import com.skanderjabouzi.nbateamviewer.data.entity.TeamDetailsEntity
import com.skanderjabouzi.nbateamviewer.data.model.TeamDetails
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import com.skanderjabouzi.nbateamviewer.di.IODispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class TeamDetailsRepository @Inject constructor(
    private val retrofitClient: RetrofitClient,
    private val teamDao: TeamDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) {

    suspend fun getSavedTeamDetails(id: Int): Flow<TeamDetailsEntity> {
        return withContext(dispatcher) {
            teamDao.getTeamDetails(id)
        }
    }

    suspend fun saveTeamDetails(teamDetails: TeamDetailsEntity) {
        withContext(dispatcher) {
            teamDao.insert(teamDetails)
        }
    }

    suspend fun getTeamsDetails(id: Int): Flow<Response<TeamDetails>> {
        return flow {
            retrofitClient.getTeamDetails(id)
        }
    }

    suspend fun getTeamExists(id: Int): Int {
        return withContext(dispatcher) {
            teamDao.getTeamExists(id)
        }
    }
}