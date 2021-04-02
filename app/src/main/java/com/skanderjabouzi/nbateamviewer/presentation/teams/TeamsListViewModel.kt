package com.skanderjabouzi.nbateamviewer.presentation.teams

import android.app.Application
import androidx.lifecycle.*
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamsListUseCase
import com.skanderjabouzi.nbateamviewer.domain.gateway.TeamsRepository
import kotlinx.coroutines.launch

class TeamsListViewModel (application: Application) : AndroidViewModel(application) {
    var repository = TeamsRepository(application)
    val usecase = TeamsListUseCase(repository)

    val teams = usecase.teamsList
    val error = usecase.error

    fun getTeams() {
        viewModelScope.launch {
            usecase.getTeams()
        }
    }

    fun sortByName() {
        viewModelScope.launch {
             usecase.sortByName()
        }
    }

    fun sortByWins() {
        viewModelScope.launch {
           usecase.sortByWins()
        }
    }

    fun sortByLosses() {
        viewModelScope.launch {
           usecase.sortByLosses()
        }
    }
}
