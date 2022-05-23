package com.skanderjabouzi.nbateamviewer

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.skanderjabouzi.nbateamviewer.data.repository.db.PlayersDao
import com.skanderjabouzi.nbateamviewer.data.repository.db.TeamDatabase
import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
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
class TeamPLayersDaoDababaseTest {
    private lateinit var playerDao: PlayersDao
    private lateinit var db: TeamDatabase
    private val mainThreadSurrogate = newSingleThreadContext("IO thread")

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        Dispatchers.setMain(mainThreadSurrogate)
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(
            context, TeamDatabase::class.java
        ).build()
        playerDao = db.playersDao()
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
    fun `write Player And Read In List`()  {
            var players: List<PlayerEntity> = listOf()
            val player: PlayerEntity = PlayerEntity(1, 1, "G", "1", "Skander Jabouzi", "0", "0", "0", "Town")
            runBlocking(Dispatchers.IO) {
                playerDao.insert(player)
                players = playerDao.getPlayers(1).first()
                assertThat(players.get(0), equalTo(player))
            }
    }
}