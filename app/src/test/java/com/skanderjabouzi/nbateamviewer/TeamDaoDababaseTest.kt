package com.skanderjabouzi.nbateamviewer

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.skanderjabouzi.nbateamviewer.data.db.TeamDao
import com.skanderjabouzi.nbateamviewer.data.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.model.db.TeamEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException


@Config(sdk = [Build.VERSION_CODES.P], manifest =Config.NONE)
@RunWith(RobolectricTestRunner::class)
class TeamDaoDababaseTest {
    private lateinit var teamDao: TeamDao
    private lateinit var db: TeamDatabase
    private val mainThreadSurrogate = newSingleThreadContext("IO thread")

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        Dispatchers.setMain(mainThreadSurrogate)
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(
            context, TeamDatabase::class.java
        ).build()
        teamDao = db.teamDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun `write Team And Read In List`() {
        var teams : List<TeamEntity> = listOf()
        val team: TeamEntity = TeamEntity(1, "Bummy Team", 44, 44)
        runBlocking(Dispatchers.IO) {
            teamDao.insert(team)
            teams = teamDao.getTeams()
            assertThat(teams.get(0), equalTo(team))
        }
    }
}