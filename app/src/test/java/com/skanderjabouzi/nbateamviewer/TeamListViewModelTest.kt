package com.skanderjabouzi.nbateamviewer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.skanderjabouzi.nbateamviewer.domain.entity.Player
import com.skanderjabouzi.nbateamviewer.domain.entity.Players
import com.skanderjabouzi.nbateamviewer.domain.entity.Team
import com.skanderjabouzi.nbateamviewer.domain.entity.Teams
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamsListUseCase
import com.skanderjabouzi.nbateamviewer.presentation.teams.TeamsListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.Is.`is`
import org.junit.*
import org.mockito.*


class TeamListViewModelTest: BaseTest() {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var usecase: TeamsListUseCase

    lateinit var viewModel: TeamsListViewModel

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<List<Player>>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)
        viewModel = TeamsListViewModel(usecase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `run getPlayers not null and validate response`() {
        runBlocking {
            doReturn(dummyTeams).whenever(usecase).getTeams()
            viewModel.getTeams()
            Assert.assertThat(
                LiveDataTestUtil.getValue(viewModel.teams), `is`(dummyTeams)
            )
            Mockito.verify(usecase).getTeams()
        }
    }

    @Test
    fun `run getPlayers null and validate response`() {
        runBlocking {
            doReturn(dummyTeamsNull).whenever(usecase).getTeams()
            viewModel.getTeams()
            Assert.assertThat(
                LiveDataTestUtil.getValue(viewModel.teams), `is`(dummyTeamsNull)
            )
            Mockito.verify(usecase).getTeams()
        }
    }

    @Test
    fun `run sortByName and validate response`() {
        runBlocking {
            doReturn(dummyTeams).whenever(usecase).sortByName()
            viewModel.sortByName()
            Assert.assertThat(
                LiveDataTestUtil.getValue(viewModel.teams), `is`(dummyTeams)
            )
            Mockito.verify(usecase).sortByName()
        }
    }

    @Test
    fun `run sortByPosition and validate response`() {
        runBlocking {
            doReturn(dummyTeams).whenever(usecase).sortByWins()
            viewModel.sortByWins()
            Assert.assertThat(
                LiveDataTestUtil.getValue(viewModel.teams), `is`(dummyTeams)
            )
            Mockito.verify(usecase).sortByWins()
        }
    }

    @Test
    fun `run sortByNumber and validate response`() {
        runBlocking {
            doReturn(dummyTeams).whenever(usecase).sortByLosses()
            viewModel.sortByLosses()
            Assert.assertThat(
                LiveDataTestUtil.getValue(viewModel.teams), `is`(dummyTeams)
            )
            Mockito.verify(usecase).sortByLosses()
        }
    }

    private val dummyTeams: List<Team>?
        get() {
            val gson = GsonBuilder().create()
            return gson.fromJson(readJsonFile("mock.api/teams.json"), Teams::class.java).teams
        }

    private val dummyTeamsNull: Players?
        get() {
            return null
        }
}