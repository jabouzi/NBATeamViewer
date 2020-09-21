package com.skanderjabouzi.nbateamviewer.domain.usecase

import com.skanderjabouzi.nbateamviewer.data.model.db.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.net.Player

class PlayerEntityConverter {
    companion object {
        fun playerEntityToPlayer(entity: PlayerEntity): Player {
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

        fun playerEntityListToPlayerList(entities: List<PlayerEntity>): List<Player> {
            val players = mutableListOf<Player>()
            for(entity in entities) {
                players.add(playerEntityToPlayer(entity))
            }

            return players
        }

        fun playerToPlayerEntity(teamId: Int, player: Player): PlayerEntity {
            return PlayerEntity(
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

        fun playerListToPlayerEntityList(teamId: Int, players: List<Player>): List<PlayerEntity> {
            val entities = mutableListOf<PlayerEntity>()
            for(player in players) {
                entities.add(playerToPlayerEntity(teamId, player))
            }

            return entities
        }
    }
}