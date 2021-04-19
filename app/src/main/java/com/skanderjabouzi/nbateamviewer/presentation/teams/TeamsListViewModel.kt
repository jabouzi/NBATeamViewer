package com.skanderjabouzi.nbateamviewer.presentation.teams

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.domain.gateway.TeamsRepository
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamsListUseCase
import kotlinx.coroutines.launch

class TeamsListViewModel(application: TeamsListUseCase, savedStateHandle: SavedStateHandle?) : AndroidViewModel(application) {
    var repository = TeamsRepository(application)
    val usecase = TeamsListUseCase(repository)

    private var savedStateHandle: SavedStateHandle? = null

    init {
        this.savedStateHandle = savedStateHandle
    }

    var teams = usecase.teamsList
    val error = usecase.error

    fun getTeams() {
        viewModelScope.launch {
            if (savedStateHandle!!.contains("EMPLOYEES_LIST")) {
                getSavedStateEmployeesList()
            } else {
                usecase.getTeams()
                saveStateEmployeesList(teams)
            }
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

    fun getSavedStateEmployeesList() {
        teams = savedStateHandle!!.getLiveData("EMPLOYEES_LIST")
    }
    fun saveStateEmployeesList(employees: LiveData<List<Team>>) {
        savedStateHandle!!.set("EMPLOYEES_LIST", employees)
    }

    fun deleteSavedStateEmployeesList() {
        savedStateHandle!!.remove<LiveData<List<Team>>>("EMPLOYEES_LIST")
    }
}
