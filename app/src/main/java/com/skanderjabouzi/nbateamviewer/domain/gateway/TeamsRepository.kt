package com.skanderjabouzi.nbateamviewer.domain.gateway

import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity
import com.skanderjabouzi.nbateamviewer.domain.model.Players
import com.skanderjabouzi.nbateamviewer.domain.model.Teams
import retrofit2.Response

interface TeamsRepository {

  suspend fun getSavedTeams(): List<TeamEntity>

  suspend fun getSavedPlayers(teamId: Int): List<PlayerEntity>

  suspend fun saveTeams(teams: List<TeamEntity>)

  suspend fun savePlayers(players: List<PlayerEntity>)

  suspend fun getTeams(): Response<Teams>

  suspend fun getPlayers(teamId: Int): Response<Players>

}