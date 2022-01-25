package com.skanderjabouzi.nbateamviewer.presentation.teams

import android.app.Application
import androidx.lifecycle.*
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.data.repository.gateway.TeamsRepository
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamsListUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsListViewModel @Inject constructor(
    val usecase: TeamsListUseCase
) : ViewModel() {

    var teams = usecase.teamsList
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
