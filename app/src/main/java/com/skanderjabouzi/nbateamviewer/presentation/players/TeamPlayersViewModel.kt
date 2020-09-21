package com.skanderjabouzi.nbateamviewer.presentation.players

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skanderjabouzi.nbateamviewer.data.model.net.Player
import com.skanderjabouzi.nbateamviewer.domain.listener.usecase.GetTeamPlayersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamPlayersViewModel @Inject constructor(val usecase: GetTeamPlayersUseCase) : ViewModel() {
    private val _players = MutableLiveData<List<Player>>()

    val players: LiveData<List<Player>>
        get() = _players

    fun getPlayers(teamId: Int) {
        viewModelScope.launch {
            try {
                _players.value = usecase.getTeamPlayers(teamId)
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _players.value = null
            }
        }
    }

    fun sortByName(teamId: Int) {
        viewModelScope.launch {
            try {
                _players.value = usecase.sortByName(teamId)
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _players.value = null
            }
        }
    }

    fun sortByPosition(teamId: Int) {
        viewModelScope.launch {
            try {
                _players.value = usecase.sortByPosition(teamId)
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _players.value = null
            }
        }
    }

    fun sortByNumber(teamId: Int) {
        viewModelScope.launch {
            try {
                _players.value = usecase.sortByNumber(teamId)
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _players.value = null
            }
        }
    }
}
