package com.skanderjabouzi.nbateamviewer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.*
import com.skanderjabouzi.nbateamviewer.data.model.net.Player
import com.skanderjabouzi.nbateamviewer.data.model.net.Players
import com.skanderjabouzi.nbateamviewer.domain.listener.usecase.GetTeamPlayersUseCase
import com.skanderjabouzi.nbateamviewer.presentation.players.TeamPlayersViewModel
import junit.framework.Assert.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TeamPlayersViewModelTest: BaseTest() {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var usecase: GetTeamPlayersUseCase

    @Mock
    lateinit var observer: Observer<List<Player>>

    lateinit var viewModel: TeamPlayersViewModel

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<List<Player>>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `run getPlayers not null and validate response`() {
        runBlocking {
            doReturn(dummyPlayers).whenever(usecase).getTeamPlayers(ArgumentMatchers.anyInt())
            viewModel = TeamPlayersViewModel(usecase)
            viewModel.players.observeForever(observer)
            viewModel.getPlayers(1)
            usecase.getTeamPlayers(ArgumentMatchers.anyInt())
            verify(observer).onChanged(viewModel.players.value)
            assertEquals(dummyPlayers, viewModel.players.value);
            assertEquals(true, viewModel.players.hasObservers());
            viewModel.players.removeObserver(observer)
        }
    }

    @Test
    fun `run getPlayers null and validate response`() {
        runBlocking {
            doReturn(dummyPlayersNull).whenever(usecase).getTeamPlayers(ArgumentMatchers.anyInt())
            viewModel = TeamPlayersViewModel(usecase)
            viewModel.players.observeForever(observer)
            viewModel.getPlayers(1)
            usecase.getTeamPlayers(ArgumentMatchers.anyInt())
            verify(observer).onChanged(viewModel.players.value)
            assertEquals(dummyPlayersNull, viewModel.players.value);
            assertEquals(true, viewModel.players.hasObservers());
            viewModel.players.removeObserver(observer)
        }
    }

    @Test
    fun `run sortByName and validate response`() {
        runBlocking {
            doReturn(dummyPlayers).whenever(usecase).sortByName(ArgumentMatchers.anyInt())
            viewModel = TeamPlayersViewModel(usecase)
            viewModel.players.observeForever(observer)
            viewModel.sortByName(1)
            usecase.sortByName(ArgumentMatchers.anyInt())
            verify(observer).onChanged(viewModel.players.value)
            assertEquals(dummyPlayers, viewModel.players.value);
            assertEquals(true, viewModel.players.hasObservers());
            viewModel.players.removeObserver(observer)
        }
    }

    @Test
    fun `run sortByPosition and validate response`() {
        runBlocking {
            doReturn(dummyPlayers).whenever(usecase).sortByPosition(ArgumentMatchers.anyInt())
            viewModel = TeamPlayersViewModel(usecase)
            viewModel.players.observeForever(observer)
            viewModel.sortByPosition(1)
            usecase.sortByPosition(ArgumentMatchers.anyInt())
            verify(observer).onChanged(viewModel.players.value)
            assertEquals(dummyPlayers, viewModel.players.value);
            assertEquals(true, viewModel.players.hasObservers());
            viewModel.players.removeObserver(observer)
        }
    }

    @Test
    fun `run sortByNumber and validate response`() {
        runBlocking {
            doReturn(dummyPlayers).whenever(usecase).sortByName(ArgumentMatchers.anyInt())
            viewModel = TeamPlayersViewModel(usecase)
            viewModel.players.observeForever(observer)
            viewModel.sortByName(1)
            usecase.sortByName(ArgumentMatchers.anyInt())
            verify(observer).onChanged(viewModel.players.value)
            assertEquals(dummyPlayers, viewModel.players.value);
            assertEquals(true, viewModel.players.hasObservers());
            viewModel.players.removeObserver(observer)
        }
    }

    private val dummyPlayers: List<Player>?
        get() {
            val gson = GsonBuilder().create()
            return gson.fromJson(readJsonFile("mock.api/1.json"), Players::class.java).players
        }

    private val dummyPlayers2: List<Player>?
        get() {
            val gson = GsonBuilder().create()
            return gson.fromJson(readJsonFile("mock.api/2.json"), Players::class.java).players
        }

    private val dummyPlayersNull: Players?
        get() {
            return null
        }
}