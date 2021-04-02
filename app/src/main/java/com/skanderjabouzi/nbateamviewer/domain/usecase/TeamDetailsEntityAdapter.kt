package com.skanderjabouzi.nbateamviewer.domain.usecase

import com.skanderjabouzi.nbateamviewer.data.entity.TeamDetailsEntity
import com.skanderjabouzi.nbateamviewer.data.model.TeamDetails

object TeamDetailsEntityAdapter {
        fun teamDetailsEntityToTeamDetails(entity: TeamDetailsEntity): TeamDetails {
            return TeamDetails(
                entity.id,
                entity.region,
                entity.name,
                entity.abbrev,
                entity.pop,
                entity.imgURL
            )
        }

        fun teamDetailsToTeamDetailsEntity(details: TeamDetails): TeamDetailsEntity {
            return TeamDetailsEntity(
                details.id,
                details.region,
                details.name,
                details.abbrev,
                details.pop,
                details.imgURL
            )
        }
}