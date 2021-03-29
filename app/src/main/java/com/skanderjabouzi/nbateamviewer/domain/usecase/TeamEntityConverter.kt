package com.skanderjabouzi.nbateamviewer.domain.usecase

import com.skanderjabouzi.nbateamviewer.data.Model.TeamModel
import com.skanderjabouzi.nbateamviewer.domain.entity.Team

object TeamEntityConverter {
        fun teamEntityToTeam(model: TeamModel): Team {
            return Team(
                model.id,
                model.name,
                model.wins,
                model.losses
            )
        }

        fun teamEntityListToTeamList(models: List<TeamModel>): List<Team> {
            val teams = mutableListOf<Team>()
            for(entity in models) {
                teams.add(teamEntityToTeam(entity))
            }

            return teams
        }

        fun teamToTeamEntity(team: Team): TeamModel {
            return TeamModel(
                team.id,
                team.name,
                team.wins,
                team.losses
            )
        }

        fun teamListToTeamEntityList(teams: List<Team>): List<TeamModel> {
            val entities = mutableListOf<TeamModel>()
            for(team in teams) {
                entities.add(teamToTeamEntity(team))
            }

            return entities
        }
}