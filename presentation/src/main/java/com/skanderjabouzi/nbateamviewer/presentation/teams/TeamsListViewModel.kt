package com.skanderjabouzi.nbateamviewer.presentation.teams

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.skanderjabouzi.nbateamviewer.domain.model.Team
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamsListUseCase
import kotlinx.coroutines.launch

class TeamsListViewModel (application: Application) : AndroidViewModel(application) {
    private val _teams = MutableLiveData<List<Team>>()
    val usecase = TeamsListUseCase(application)

    val teams: LiveData<List<Team>>
        get() = _teams

    fun getTeams() {
        viewModelScope.launch {
            try {
                _teams.value = usecase.getTeams()
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _teams.value = null
            }
        }
    }

    fun sortByName() {
        viewModelScope.launch {
            try {
                _teams.value = usecase.sortByName()
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _teams.value = null
            }
        }
    }

    fun sortByWins() {
        viewModelScope.launch {
            try {
                _teams.value = usecase.sortByWins()
            } catch (e: Exception) {
                    Log.e("listResult", "${e.localizedMessage}")
                _teams.value = null
            }
        }
    }

    fun sortByLosses() {
        viewModelScope.launch {
            try {
                _teams.value = usecase.sortByLosses()
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
            _teams.value = null
            }
        }
    }
}
