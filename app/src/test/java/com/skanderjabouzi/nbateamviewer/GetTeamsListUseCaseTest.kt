package com.manulife.mobile.gb.recentclaims

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skanderjabouzi.nbateamviewer.BaseTest
import com.skanderjabouzi.nbateamviewer.data.entity.TeamEntity
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.data.model.Teams
import com.skanderjabouzi.nbateamviewer.data.repository.gateway.TeamsRepository
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamsListUseCase
import com.skanderjabouzi.nbateamviewer.domain.helpers.SortType
import com.skanderjabouzi.nbateamviewer.domain.helpers.TeamEntityAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetTeamsListUseCaseTest: BaseTest() {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TeamsRepository

    private lateinit var usecase: TeamsListUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        usecase = TeamsListUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Run getTeams and get repo from api is not called`() {
        runBlocking {
            doReturn(dummyTeamsFromDb).whenever(repository).getSavedTeams()
            usecase.getTeams()            
            verify(repository, never()).getTeams()
            val teams = usecase.teamsList.value!!
            Assert.assertEquals(30, teams.size)
        }
    }

    @Test
    fun `Run getTeams and get repo from db api is called`() {
        runBlocking {
            doReturn(dummyTeamsEmpty).whenever(repository).getSavedTeams()
            doReturn(dummyTeamsfromApi).whenever(repository).getTeams()
            usecase.getTeams()
            verify(repository).getTeams()
            val teams = usecase.teamsList.value!!
            Assert.assertEquals(30, teams.size)
        }
    }

    @Test
    fun `Run sortbyName ascending and verify the order`() {
        runBlocking {
            TeamsListUseCase.sortName = SortType.ASCENDING
            doReturn(dummyTeamsFromDb).whenever(repository).getSavedTeams()
            usecase.sortByName()
            val teams = usecase.teamsList.value!!
            Assert.assertEquals("Atlanta Hawks", teams.first().name)
            Assert.assertEquals("Washington Wizards", teams.last().name)
        }
    }

    @Test
    fun `Run sortbyName descending and verify the order`() {
        runBlocking {
            TeamsListUseCase.sortName = SortType.DESCENDING
            doReturn(dummyTeamsFromDb).whenever(repository).getSavedTeams()
            usecase.sortByName()
            val teams = usecase.teamsList.value!!
            Assert.assertEquals("Washington Wizards", teams.first().name)
            Assert.assertEquals("Atlanta Hawks", teams.last().name)
        }
    }

    @Test
    fun `Run sortbyWins ascending and verify the order`() {
        runBlocking {
            TeamsListUseCase.sortWins = SortType.ASCENDING
            doReturn(dummyTeamsFromDb).whenever(repository).getSavedTeams()
            usecase.sortByWins()
            val teams = usecase.teamsList.value!!
            Assert.assertEquals(15, teams.first().wins)
            Assert.assertEquals(56, teams.last().wins)
        }
    }

    @Test
    fun `Run sortbyWins descending and verify the order`() {
        runBlocking {
            TeamsListUseCase.sortWins = SortType.DESCENDING
            doReturn(dummyTeamsFromDb).whenever(repository).getSavedTeams()
            usecase.sortByWins()
            val teams = usecase.teamsList.value!!
            Assert.assertEquals(56, teams.first().wins)
            Assert.assertEquals(15, teams.last().wins)
        }
    }

    @Test
    fun `Run sortbyLosses ascending and verify the order`() {
        runBlocking {
            TeamsListUseCase.sortLosses = SortType.ASCENDING
            doReturn(dummyTeamsFromDb).whenever(repository).getSavedTeams()
            usecase.sortByLosses()
            val teams = usecase.teamsList.value!!
            Assert.assertEquals(17, teams.first().losses)
            Assert.assertEquals(50, teams.last().losses)
        }
    }

    @Test
    fun `Run sortbyLosses descending and verify the order`() {
        runBlocking {
            TeamsListUseCase.sortLosses = SortType.DESCENDING
            doReturn(dummyTeamsFromDb).whenever(repository).getSavedTeams()
             usecase.sortByLosses()
            val teams = usecase.teamsList.value!!
            Assert.assertEquals(50, teams.first().losses)
            Assert.assertEquals(17, teams.last().losses)
        }
    }

    private val dummyTeamsFromDb: List<TeamEntity>
        get() {
            val gson = GsonBuilder().create()
            return TeamEntityAdapter.teamListToTeamEntityList(gson.fromJson(readJsonFile("mock.api/teams.json"), Teams::class.java).teams!!)
        }

    private val dummyTeamsfromApi: List<Team>?
        get() {
            val gson = GsonBuilder().create()
            return gson.fromJson(readJsonFile("mock.api/teams.json"), Teams::class.java).teams
        }

    private val dummyTeamsEmpty: List<Team>
        get() {
            return listOf()
        }
}