package com.skanderjabouzi.nbateamviewer.domain.usecase

import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import com.skanderjabouzi.nbateamviewer.domain.model.Player

object PlayerEntityConverter {
        fun playerEntityToPlayer(entity: com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity): Player {
            return Player(
                entity.position,
                entity.number,
                entity.full_name,
                entity.height,
                entity.weight,
                entity.date_of_birth,
                entity.from
            )
        }

        fun playerEntityListToPlayerList(entities: List<com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity>): List<Player> {
            val players = mutableListOf<Player>()
            for(entity in entities) {
                players.add(playerEntityToPlayer(entity))
            }

            return players
        }

        fun playerToPlayerEntity(teamId: Int, player: Player): com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity {
            return com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity(
                    null,
                    teamId,
                    player.position,
                    player.number,
                    player.full_name,
                    player.height,
                    player.weight,
                    player.date_of_birth,
                    player.from
            )
        }

        fun playerListToPlayerEntityList(teamId: Int, players: List<Player>): List<com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity> {
            val entities = mutableListOf<com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity>()
            for(player in players) {
                entities.add(playerToPlayerEntity(teamId, player))
            }

            return entities
        }
}