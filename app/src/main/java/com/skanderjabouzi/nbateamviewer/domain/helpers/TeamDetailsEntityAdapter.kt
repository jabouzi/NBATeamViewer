package com.skanderjabouzi.nbateamviewer.domain.helpers

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
                details.id ?: 0,
                details.region ?: "",
                details.name ?: "",
                details.abbrev ?: "",
                details.pop ?: 0.0,
                details.imgURL ?: ""
            )
        }
}