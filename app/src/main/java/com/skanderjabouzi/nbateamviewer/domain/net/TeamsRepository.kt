package com.skanderjabouzi.nbateamviewer.domain.net

import com.skanderjabouzi.nbateamviewer.data.model.db.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.db.TeamEntity
import com.skanderjabouzi.nbateamviewer.data.model.net.Player
import com.skanderjabouzi.nbateamviewer.data.model.net.Players
import com.skanderjabouzi.nbateamviewer.data.model.net.Team
import com.skanderjabouzi.nbateamviewer.data.model.net.Teams
import retrofit2.Response

interface TeamsRepository {

  suspend fun getSavedTeams(): List<TeamEntity>

  suspend fun getSavedPlayers(teamId: Int): List<PlayerEntity>

  suspend fun saveTeams(teams: List<TeamEntity>)

  suspend fun savePlayers(players: List<PlayerEntity>)

  suspend fun getTeams(): Response<Teams>

  suspend fun getPlayers(teamId: Int): Response<Players>

}