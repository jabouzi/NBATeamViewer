package com.skanderjabouzi.nbateamviewer.data.repository.gateway

import com.skanderjabouzi.nbateamviewer.data.repository.db.PlayersDao
import com.skanderjabouzi.nbateamviewer.data.entity.PlayerEntity
import com.skanderjabouzi.nbateamviewer.data.model.Players
import com.skanderjabouzi.nbateamviewer.data.repository.net.RetrofitClient
import com.skanderjabouzi.nbateamviewer.di.IODispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class TeamPlayersRepository @Inject constructor(
    private val retrofitClient: RetrofitClient,
    private val playersDao: PlayersDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) {

    suspend fun getSavedPlayers(teamId: Int): Flow<List<PlayerEntity>> {
        return withContext(dispatcher) {
            playersDao.getPlayers(teamId)
        }
    }

    suspend fun savePlayers(players: List<PlayerEntity>) {
        withContext(dispatcher) {
            for (player in players) {
                playersDao.insert(player)
            }
        }
    }

    suspend fun getPlayers(teamId: Int): Flow<Response<Players>> {
        return flow {
            retrofitClient.getPlayers(teamId)
        }
    }

    suspend fun getPlayerExists(id: Int): Int {
        return withContext(dispatcher) {
            playersDao.getPlayerExists(id)
        }
    }
}