package com.skanderjabouzi.nbateamviewer.presentation.players

import androidx.lifecycle.*
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamPlayersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamPlayersViewModel @Inject constructor(
    val usecase: TeamPlayersUseCase
) : ViewModel() {

    var players = usecase.playersList
    var error = usecase.error

    fun getPlayers(teamId: Int) {
        viewModelScope.launch {
            usecase.getTeamPlayers(teamId)
        }
    }

    fun sortByName(teamId: Int) {
        viewModelScope.launch {
            usecase.sortByName(teamId)
        }
    }

    fun sortByPosition(teamId: Int) {
        viewModelScope.launch {
            usecase.sortByPosition(teamId)
        }
    }

    fun sortByNumber(teamId: Int) {
        viewModelScope.launch {
            usecase.sortByNumber(teamId)
        }
    }
}
