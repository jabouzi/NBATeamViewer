package com.skanderjabouzi.nbateamviewer.domain.gateway

import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity
import com.skanderjabouzi.nbateamviewer.domain.model.Players
import com.skanderjabouzi.nbateamviewer.domain.model.Teams
import retrofit2.Response

interface TeamsRepository {

  suspend fun getSavedTeams(): List<com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity>

  suspend fun getSavedPlayers(teamId: Int): List<com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity>

  suspend fun saveTeams(teams: List<com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity>)

  suspend fun savePlayers(players: List<com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity>)

  suspend fun getTeams(): Response<Teams>

  suspend fun getPlayers(teamId: Int): Response<Players>

}