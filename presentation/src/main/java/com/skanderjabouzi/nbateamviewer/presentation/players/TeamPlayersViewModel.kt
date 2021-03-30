package com.skanderjabouzi.nbateamviewer.presentation.players

import android.app.Application
import androidx.lifecycle.*
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamPlayersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamPlayersViewModel (application: Application) : AndroidViewModel(application) {

    val usecase = TeamPlayersUseCase(application)

    var players = usecase.playersList
    var error = usecase.error

    fun getPlayers(teamId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            usecase.getTeamPlayers(teamId)
        }
    }

    fun sortByName(teamId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            usecase.sortByName(teamId)
        }
    }

    fun sortByPosition(teamId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            usecase.sortByPosition(teamId)
        }
    }

    fun sortByNumber(teamId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            usecase.sortByNumber(teamId)
        }
    }
}