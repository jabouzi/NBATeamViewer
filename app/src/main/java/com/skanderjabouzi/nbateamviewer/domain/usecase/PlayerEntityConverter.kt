package com.skanderjabouzi.nbateamviewer.domain.usecase

import com.skanderjabouzi.nbateamviewer.data.Model.PlayerModel
import com.skanderjabouzi.nbateamviewer.domain.entity.Player

object PlayerEntityConverter {
        fun playerEntityToPlayer(model: PlayerModel): Player {
            return Player(
                model.position,
                model.number,
                model.full_name,
                model.height,
                model.weight,
                model.date_of_birth,
                model.from
            )
        }

        fun playerEntityListToPlayerList(models: List<PlayerModel>): List<Player> {
            val players = mutableListOf<Player>()
            for(entity in models) {
                players.add(playerEntityToPlayer(entity))
            }

            return players
        }

        fun playerToPlayerEntity(teamId: Int, player: Player): PlayerModel {
            return PlayerModel(
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

        fun playerListToPlayerEntityList(teamId: Int, players: List<Player>): List<PlayerModel> {
            val entities = mutableListOf<PlayerModel>()
            for(player in players) {
                entities.add(playerToPlayerEntity(teamId, player))
            }

            return entities
        }
}