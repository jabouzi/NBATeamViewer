package com.skanderjabouzi.nbateamviewer.domain.gateway

import com.skanderjabouzi.nbateamviewer.data.Model.PlayerModel
import com.skanderjabouzi.nbateamviewer.data.Model.TeamModel
import com.skanderjabouzi.nbateamviewer.domain.entity.Players
import com.skanderjabouzi.nbateamviewer.domain.entity.Teams
import retrofit2.Response

interface TeamsRepository {

  suspend fun getSavedTeams(): List<TeamModel>

  suspend fun getSavedPlayers(teamId: Int): List<PlayerModel>

  suspend fun saveTeams(teams: List<TeamModel>)

  suspend fun savePlayers(players: List<PlayerModel>)

  suspend fun getTeams(): Response<Teams>

  suspend fun getPlayers(teamId: Int): Response<Players>

}