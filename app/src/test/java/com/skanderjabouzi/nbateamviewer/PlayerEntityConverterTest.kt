package com.skanderjabouzi.nbateamviewer

import android.os.Build
import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.Player
import com.skanderjabouzi.nbateamviewer.domain.usecase.PlayerEntityAdapter
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class PlayerEntityConverterTest {

    val playerEntity = PlayerEntity(1,
        1,"G",
        "1", "Skander",
        "0", "0", "0",
        "Town")

    val playerEntity2 = PlayerEntity(2,
        1,"C",
        "11", "Jabouzi",
        "0", "0", "0",
        "Town2")

    val player = Player(
        "G/C","33", "Bob",
        "1", "1", "1",
        "City")

    val player2 = Player(
        "F/C","22", "Robert",
        "1", "1", "1",
        "City2")

    @Test
    fun `test PlayerEntity To Player`() {
        val player = PlayerEntityAdapter.playerEntityToPlayer(playerEntity)
        Assert.assertEquals("Skander", player.full_name)
        Assert.assertEquals("G", player.position)
        Assert.assertEquals("1", player.number)
    }

    @Test
    fun `test Player To PlayerEntity`() {
        val playerEntity = PlayerEntityAdapter.playerToPlayerEntity(1, player)
        Assert.assertEquals("Bob", playerEntity.full_name)
        Assert.assertEquals("G/C", playerEntity.position)
        Assert.assertEquals("33", playerEntity.number)
        Assert.assertEquals(null, playerEntity.id)
    }

    @Test
    fun `test PlayerEntityList To PlayerList`() {
        val playersEntity = listOf(playerEntity, playerEntity2)
        val players = PlayerEntityAdapter.playerEntityListToPlayerList(playersEntity)
        Assert.assertEquals("Skander", players[0].full_name)
        Assert.assertEquals("G", players[0].position)
        Assert.assertEquals("1", players[0].number)
        Assert.assertEquals("Jabouzi", players[1].full_name)
        Assert.assertEquals("C", players[1].position)
        Assert.assertEquals("11", players[1].number)
    }

    @Test
    fun `test PlayerList To PlayerEntityList`() {
        val players = listOf(player, player2)
        val playersEntity = PlayerEntityAdapter.playerListToPlayerEntityList(1, players)
        Assert.assertEquals("Bob", playersEntity[0].full_name)
        Assert.assertEquals("G/C", playersEntity[0].position)
        Assert.assertEquals("33", playersEntity[0].number)
        Assert.assertEquals(null, playersEntity[0].id)
        Assert.assertEquals("Robert", playersEntity[1].full_name)
        Assert.assertEquals("F/C", playersEntity[1].position)
        Assert.assertEquals("22", playersEntity[1].number)
        Assert.assertEquals(null, playersEntity[1].id)
    }

}