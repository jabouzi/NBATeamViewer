package com.skanderjabouzi.nbateamviewer.domain.usecase

import com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity
import com.skanderjabouzi.nbateamviewer.domain.model.Team

object TeamEntityConverter {
        fun teamEntityToTeam(entity: com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity): Team {
            return Team(
                entity.id,
                entity.name,
                entity.wins,
                entity.losses
            )
        }

        fun teamEntityListToTeamList(entities: List<com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity>): List<Team> {
            val teams = mutableListOf<Team>()
            for(entity in entities) {
                teams.add(teamEntityToTeam(entity))
            }

            return teams
        }

        fun teamToTeamEntity(team: Team): com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity {
            return com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity(
                    team.id,
                    team.name,
                    team.wins,
                    team.losses
            )
        }

        fun teamListToTeamEntityList(teams: List<Team>): List<com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity> {
            val entities = mutableListOf<com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity>()
            for(team in teams) {
                entities.add(teamToTeamEntity(team))
            }

            return entities
        }
}