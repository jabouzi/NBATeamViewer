package com.skanderjabouzi.nbateamviewer.domain.gateway

import android.content.Context
import androidx.lifecycle.LiveData
import com.skanderjabouzi.nbateamviewer.data.entity.TeamDetailsEntity
import com.skanderjabouzi.nbateamviewer.data.model.TeamDetails
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.repository.net.Network
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import retrofit2.Response

class TeamDetailsRepository(val context: Context) {

    var db = TeamDatabase.getInstance(context)
    var retrofitClient: RetrofitClient = RetrofitClient(Network.getRetrofit(context))
    private val teamDao: TeamDao = db.teamDao()

    fun getSavedTeamDetails(id: Int): LiveData<TeamDetailsEntity> {
        return teamDao.getTeamDetails(id)
    }

    suspend fun saveTeamDetails(teamDetails: TeamDetailsEntity) {
            teamDao.insert(teamDetails)
    }

    suspend fun getTeamsDetails(id: Int): Response<TeamDetails> {
        return retrofitClient.getTeamDetails(id)
    }
}