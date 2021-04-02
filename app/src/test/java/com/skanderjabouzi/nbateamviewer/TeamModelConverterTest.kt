package com.skanderjabouzi.nbateamviewer

import android.os.Build
import com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamEntityAdapter
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class TeamModelConverterTest {

    val teamEntity = TeamEntity(
        1,
        "Toronto",
        55,
        22
    )

    val teamEntity2 = TeamEntity(
        2,
        "New York",
        44,
        33
    )

    val team = Team(
        3,
        "Los Angeles",
        50,
        30
    )

    val team2 = Team(
        4,
        "Boston",
        44,
        28
    )

    @Test
    fun `test Team Entity To Team`() {
        val team = TeamEntityAdapter.teamEntityToTeam(teamEntity)
        Assert.assertEquals(1, team.id)
        Assert.assertEquals("Toronto", team.name)
        Assert.assertEquals(55, team.wins)
        Assert.assertEquals(22, team.losses)
    }

    @Test
    fun `test Team To TeamEntity`() {
        val teamEntity = TeamEntityAdapter.teamToTeamEntity(team)
        Assert.assertEquals(3, team.id)
        Assert.assertEquals("Los Angeles", team.name)
        Assert.assertEquals(50, team.wins)
        Assert.assertEquals(30, team.losses)
    }

    @Test
    fun `test Team EntityList To TeamList`() {
        val teamsEntity = listOf(teamEntity, teamEntity2)
        val teams = TeamEntityAdapter.teamEntityListToTeamList(teamsEntity)
        Assert.assertEquals(1, teams[0].id)
        Assert.assertEquals("Toronto", teams[0].name)
        Assert.assertEquals(55, teams[0].wins)
        Assert.assertEquals(22, teams[0].losses)
        Assert.assertEquals(2, teams[1].id)
        Assert.assertEquals("New York", teams[1].name)
        Assert.assertEquals(44, teams[1].wins)
        Assert.assertEquals(33, teams[1].losses)
    }

    @Test
    fun `test TeamList To TeamEntity List`() {
        val teams = listOf(team, team2)
        val teamsEntity = TeamEntityAdapter.teamListToTeamEntityList(teams)
        Assert.assertEquals(3, teamsEntity[0].id)
        Assert.assertEquals("Los Angeles", teamsEntity[0].name)
        Assert.assertEquals(50, teamsEntity[0].wins)
        Assert.assertEquals(30, teamsEntity[0].losses)
        Assert.assertEquals(4, teamsEntity[1].id)
        Assert.assertEquals("Boston", teams[1].name)
        Assert.assertEquals(44, teamsEntity[1].wins)
        Assert.assertEquals(28, teamsEntity[1].losses)
    }
}