package com.skanderjabouzi.nbateamviewer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.Player
import com.skanderjabouzi.nbateamviewer.data.model.Players
import com.skanderjabouzi.nbateamviewer.data.repository.gateway.TeamPlayersRepository
import com.skanderjabouzi.nbateamviewer.data.repository.gateway.TeamsRepository
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamPlayersUseCase
import com.skanderjabouzi.nbateamviewer.domain.helpers.PlayerEntityAdapter
import com.skanderjabouzi.nbateamviewer.domain.helpers.SortType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class GetTeamPlayersListUseCaseTest: BaseTest() {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TeamPlayersRepository

    private lateinit var usecase: TeamPlayersUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        usecase = TeamPlayersUseCase(repository)
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
            usecase.playersList.value
            Assert.assertEquals(15, usecase.playersList.value?.size)
        }
    }

    @Test
    fun `Run getTeamPlayers and get repo from api is called`() {
        runBlocking {
            doReturn(dummyPlayersEmpty).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            doReturn(dummyPlayersFromApi).whenever(repository).getPlayers(ArgumentMatchers.anyInt())
            usecase.getTeamPlayers(1)
            verify(repository).getPlayers(ArgumentMatchers.anyInt())
            Assert.assertEquals(15, usecase.playersList.value?.size)
        }
    }


    @Test
    fun `Run sortbyName ascending and verify the order`() {
        runBlocking {
            TeamPlayersUseCase.sortName = SortType.ASCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            val players = usecase.sortByName(1)
            Assert.assertEquals("Bembry, DeAndre' (FA)", usecase.playersList.value?.first()?.full_name?.trim())
            Assert.assertEquals("Young, Trae", usecase.playersList.value?.last()?.full_name?.trim())
        }
    }

    @Test
    fun `Run sortbyName descending and verify the order`() {
        runBlocking {
            TeamPlayersUseCase.sortName = SortType.DESCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            val players = usecase.sortByName(1)
            Assert.assertEquals("Young, Trae", usecase.playersList.value?.first()?.full_name?.trim())
            Assert.assertEquals("Bembry, DeAndre' (FA)", usecase.playersList.value?.last()?.full_name?.trim())
        }
    }

    @Test
    fun `Run sortbyPosition ascending and verify the order`() {
        runBlocking {
            TeamPlayersUseCase.sortPosition = SortType.ASCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            usecase.sortByPosition(1)
            val players = usecase.playersList.value!!
            Assert.assertEquals("C", players.first().position)
            Assert.assertEquals("G/F", players.last().position)
        }
    }

    @Test
    fun `Run sortbyPosition descending and verify the order`() {
        runBlocking {
            TeamPlayersUseCase.sortPosition = SortType.DESCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            usecase.sortByPosition(1)
            val players = usecase.playersList.value!!
            Assert.assertEquals("G/F", players.first().position)
            Assert.assertEquals("C", players.last().position)
        }
    }

    @Test
    fun `Run sortbyNumber ascending and verify the order`() {
        runBlocking {
            TeamPlayersUseCase.sortNumber = SortType.ASCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            usecase.sortByNumber(1)
            val players = usecase.playersList.value!!
            Assert.assertEquals("0", players.first().number)
            Assert.assertEquals("95", players.last().number)
        }
    }

    @Test
    fun `Run sortbyNumber descending and verify the order`() {
        runBlocking {
            TeamPlayersUseCase.sortNumber = SortType.DESCENDING
            doReturn(dummyPlayersFromDb).whenever(repository).getSavedPlayers(ArgumentMatchers.anyInt())
            usecase.sortByNumber(1)
            val players = usecase.playersList.value!!
            Assert.assertEquals("95", players.first().number)
            Assert.assertEquals("0", players.last().number)
        }
    }

    private val dummyPlayersFromDb: Flow<List<PlayerEntity>>
        get() {
            val gson = GsonBuilder().create()
            return flow {
                emit(PlayerEntityAdapter.playerListToPlayerEntityList(1, gson.fromJson(readJsonFile("mock.api/1.json"), Players::class.java).players!!))
            }
        }

    private val dummyPlayersFromApi: Response<Players>
        get() {
            val gson = GsonBuilder().create()
            val mockResponseBody = Mockito.mock(Response::class.java)
            val mockResponse = Response.success(gson.fromJson(readJsonFile("mock.api/1.json"), Players::class.java)!!)
            return mockResponse
        }

    private val dummyPlayersEmpty: Flow<List<PlayerEntity>>
        get() {
            val gson = GsonBuilder().create()
            return flow {
                emit(PlayerEntityAdapter.playerListToPlayerEntityList(1, gson.fromJson(readJsonFile("mock.api/emptyPlayers.json"), Players::class.java).players!!))
            }
        }
}