package com.skanderjabouzi.nbateamviewer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.skanderjabouzi.nbateamviewer.domain.model.Player
import com.skanderjabouzi.nbateamviewer.domain.model.Players
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamPlayersUseCase
import com.skanderjabouzi.nbateamviewer.presentation.players.TeamPlayersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.Is.`is`
import org.junit.*
import org.mockito.*
import org.robolectric.RuntimeEnvironment.application


class TeamPlayersViewModelTest: BaseTest() {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var usecase: TeamPlayersUseCase

    lateinit var viewModel: TeamPlayersViewModel

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<List<Player>>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)
        viewModel = TeamPlayersViewModel(application)
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
            viewModel.getPlayers(2)
            Assert.assertThat(
                LiveDataTestUtil.getValue(viewModel.players), `is`(dummyPlayers)
            )
            Mockito.verify(usecase).getTeamPlayers(2)
        }
    }

    @Test
    fun `run getPlayers null and validate response`() {
        runBlocking {
            doReturn(dummyPlayersNull).whenever(usecase).getTeamPlayers(ArgumentMatchers.anyInt())
            viewModel.getPlayers(1)
            Assert.assertThat(
                LiveDataTestUtil.getValue(viewModel.players), `is`(dummyPlayersNull)
            )
            Mockito.verify(usecase).getTeamPlayers(1)
        }
    }

    @Test
    fun `run sortByName and validate response`() {
        runBlocking {
            doReturn(dummyPlayers).whenever(usecase).sortByName(ArgumentMatchers.anyInt())
            viewModel.sortByName(1)
            Assert.assertThat(
                LiveDataTestUtil.getValue(viewModel.players), `is`(dummyPlayers)
            )
            Mockito.verify(usecase).sortByName(1)
        }
    }

    @Test
    fun `run sortByPosition and validate response`() {
        runBlocking {
            doReturn(dummyPlayers).whenever(usecase).sortByPosition(ArgumentMatchers.anyInt())
            viewModel.sortByPosition(1)
            Assert.assertThat(
                LiveDataTestUtil.getValue(viewModel.players), `is`(dummyPlayers)
            )
            Mockito.verify(usecase).sortByPosition(1)
        }
    }

    @Test
    fun `run sortByNumber and validate response`() {
        runBlocking {
            doReturn(dummyPlayers).whenever(usecase).sortByNumber(ArgumentMatchers.anyInt())
            viewModel.sortByNumber(1)
            Assert.assertThat(
                LiveDataTestUtil.getValue(viewModel.players), `is`(dummyPlayers)
            )
            Mockito.verify(usecase).sortByNumber(1)
        }
    }

    private val dummyPlayers: List<Player>?
        get() {
            val gson = GsonBuilder().create()
            return gson.fromJson(readJsonFile("mock.api/1.json"), Players::class.java).players
        }

    private val dummyPlayersNull: Players?
        get() {
            return null
        }
}