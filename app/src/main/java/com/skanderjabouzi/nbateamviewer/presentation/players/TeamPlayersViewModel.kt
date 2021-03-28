package com.skanderjabouzi.nbateamviewer.presentation.players

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.skanderjabouzi.nbateamviewer.data.model.net.Player
import com.skanderjabouzi.nbateamviewer.domain.listener.usecase.TeamPlayersUseCase
import com.skanderjabouzi.nbateamviewer.domain.net.TeamsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamPlayersViewModel (application: Application) : AndroidViewModel(application) {
    private val _players = MutableLiveData<List<Player>>()
    var teamsRepository = TeamsRepositoryImpl(application)
    val usecase = TeamPlayersUseCase(teamsRepository)

    val players: LiveData<List<Player>>
        get() = _players

    fun getPlayers(teamId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _players.value = usecase.getTeamPlayers(teamId)
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _players.value = null
            }
        }
    }

    fun sortByName(teamId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _players.value = usecase.sortByName(teamId)
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _players.value = null
            }
        }
    }

    fun sortByPosition(teamId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _players.value = usecase.sortByPosition(teamId)
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _players.value = null
            }
        }
    }

    fun sortByNumber(teamId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _players.value = usecase.sortByNumber(teamId)
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _players.value = null
            }
        }
    }
}
