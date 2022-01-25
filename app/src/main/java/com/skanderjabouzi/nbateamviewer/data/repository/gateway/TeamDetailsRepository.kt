package com.skanderjabouzi.nbateamviewer.data.repository.gateway

import android.content.Context
import androidx.lifecycle.LiveData
import com.skanderjabouzi.nbateamviewer.data.entity.TeamDetailsEntity
import com.skanderjabouzi.nbateamviewer.data.model.TeamDetails
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class TeamDetailsRepository @Inject constructor() {

    @Inject lateinit var retrofitClient: RetrofitClient
    @Inject lateinit var teamDao: TeamDao

    fun getSavedTeamDetails(id: Int): LiveData<TeamDetailsEntity?> {
        return teamDao.getTeamDetails(id)
    }

    suspend fun saveTeamDetails(teamDetails: TeamDetailsEntity) {
            teamDao.insert(teamDetails)
    }

    suspend fun getTeamsDetails(id: Int): Response<TeamDetails> {
        return retrofitClient.getTeamDetails(id)
    }
}