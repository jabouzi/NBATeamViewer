package com.skanderjabouzi.nbateamviewer.domain.usecase

import com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity
import com.skanderjabouzi.nbateamviewer.data.model.Team

object TeamEntityAdapter {
        fun teamEntityToTeam(entity: TeamEntity): Team {
            return Team(
                entity.id,
                entity.name,
                entity.wins,
                entity.losses,
                entity.imgURL
            )
        }

        fun teamEntityListToTeamList(entities: List<TeamEntity>): List<Team> {
            val teams = mutableListOf<Team>()
            for(entity in entities) {
                teams.add(teamEntityToTeam(entity))
            }

            return teams
        }

        fun teamToTeamEntity(team: Team): TeamEntity {
            return TeamEntity(
                team.id ?: 0,
                team.name ?: "",
                team.wins ?: 0,
                team.losses ?: 0,
                team.imgURL ?: ""
            )
        }

        fun teamListToTeamEntityList(teams: List<Team>): List<TeamEntity> {
            val entities = mutableListOf<TeamEntity>()
            for(team in teams) {
                entities.add(teamToTeamEntity(team))
            }

            return entities
        }
}