package com.skanderjabouzi.nbateamviewer.domain.net

import com.skanderjabouzi.nbateamviewer.data.model.db.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.db.TeamEntity
import com.skanderjabouzi.nbateamviewer.data.model.net.Player
import com.skanderjabouzi.nbateamviewer.data.model.net.Team

interface TeamsRepository {

  suspend fun getSavedTeams(): List<TeamEntity>

  suspend fun getSavedPlayers(teamId: Int): List<PlayerEntity>

  suspend fun saveTeams(teams: List<TeamEntity>)

  suspend fun savePlayers(players: List<PlayerEntity>)

  suspend fun getTeams(): List<Team>

  suspend fun getPlayers(teamId: Int): List<Player>

}