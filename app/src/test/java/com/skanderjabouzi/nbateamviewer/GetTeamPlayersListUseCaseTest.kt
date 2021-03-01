package com.skanderjabouzi.nbateamviewer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skanderjabouzi.nbateamviewer.data.model.db.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.net.Player
import com.skanderjabouzi.nbateamviewer.data.model.net.Players
import com.skanderjabouzi.nbateamviewer.domain.net.TeamsRepository
import com.skanderjabouzi.nbateamviewer.domain.listener.usecase.GetTeamPlayersUseCase
import com.skanderjabouzi.nbateamviewer.domain.usecase.PlayerEntityConverter
import com.skanderjabouzi.nbateamviewer.domain.usecase.SortType
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTeamPlayersListUseCaseTest: BaseTest() {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TeamsRepository

    private lateinit var usecase: GetTeamPlayersUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        usecase = GetTeamPlayersUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Run getTeamPlayers and get repo from api is not called`() {
        runBlocking {
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            val players = usecase.getTeamPlayers(1)
            verify(repository, never()).getPlayers(ArgumentMatchers.anyInt())
            Assert.assertEquals(17, players.size)
        }
    }

    @Test
    fun `Run getTeamPlayers and get repo from api is called`() {
        runBlocking {
            doReturn(dummyPlayersEmpty).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            doReturn(dummyPlayersFromApi).whenever(repository).getPlayers(ArgumentMatchers.anyInt())
            val players = usecase.getTeamPlayers(1)
            verify(repository).getPlayers(ArgumentMatchers.anyInt())
            Assert.assertEquals(17, players.size)
        }
    }


    @Test
    fun `Run sortbyName ascending and verify the order`() {
        runBlocking {
            GetTeamPlayersUseCase.sortByName = SortType.ASCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            val players = usecase.sortByName(1)
            Assert.assertEquals("Aldridge, LaMarcus", players.first().full_name.trim())
            Assert.assertEquals("Šamanić, Luka", players.last().full_name.trim())
        }
    }

    @Test
    fun `Run sortbyName descending and verify the order`() {
        runBlocking {
            GetTeamPlayersUseCase.sortByName = SortType.DESCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            val players = usecase.sortByName(1)
            Assert.assertEquals("Šamanić, Luka", players.first().full_name.trim())
            Assert.assertEquals("Aldridge, LaMarcus", players.last().full_name.trim())
        }
    }

    @Test
    fun `Run sortbyPosition ascending and verify the order`() {
        runBlocking {
            GetTeamPlayersUseCase.sortByPosition = SortType.ASCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            val players = usecase.sortByPosition(1)
            Assert.assertEquals("C", players.first().position)
            Assert.assertEquals("G/F", players.last().position)
        }
    }

    @Test
    fun `Run sortbyPosition descending and verify the order`() {
        runBlocking {
            GetTeamPlayersUseCase.sortByPosition = SortType.DESCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            val players = usecase.sortByPosition(1)
            Assert.assertEquals("G/F", players.first().position)
            Assert.assertEquals("C", players.last().position)
        }
    }

    @Test
    fun `Run sortbyNumber ascending and verify the order`() {
        runBlocking {
            GetTeamPlayersUseCase.sortByNumber = SortType.ASCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            val players = usecase.sortByNumber(1)
            Assert.assertEquals("1", players.first().number)
            Assert.assertEquals("45", players.last().number)
        }
    }

    @Test
    fun `Run sortbyNumber descending and verify the order`() {
        runBlocking {
            GetTeamPlayersUseCase.sortByNumber = SortType.DESCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            val players = usecase.sortByNumber(1)
            Assert.assertEquals("45", players.first().number)
            Assert.assertEquals("1", players.last().number)
        }
    }

    private val dummyPlayersFromDb: List<PlayerEntity>
        get() {
            val gson = GsonBuilder().create()
            return PlayerEntityConverter.playerListToPlayerEntityList(1, gson.fromJson(readJsonFile("mock.api/1.json"), Players::class.java).players)
        }

    private val dummyPlayersFromApi: List<Player>
        get() {
            val gson = GsonBuilder().create()
            return gson.fromJson(readJsonFile("mock.api/1.json"), Players::class.java).players
        }

    private val dummyPlayersEmpty: List<PlayerEntity>?
        get() {
            return listOf()
        }
}