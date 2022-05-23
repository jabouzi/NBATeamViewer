package com.skanderjabouzi.nbateamviewer.presentation.teams

import android.util.Log
import androidx.lifecycle.*
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsListViewModel @Inject constructor(
    val usecase: TeamsListUseCase
) : ViewModel() {

    private var _teams = MutableLiveData<List<Team>>()
    val teams:  LiveData<List<Team>>
        get() = _teams
    val error = usecase.error

    fun getTeams() {
        viewModelScope.launch {
            _teams.value = usecase.getTeams()
            Log.e("####2", "${teams.value}")
        }
    }

    fun sortByName() {
        viewModelScope.launch {
            _teams.value = usecase.sortByName()
        }
    }

    fun sortByWins() {
        viewModelScope.launch {
            _teams.value = usecase.sortByWins()
        }
    }

    fun sortByLosses() {
        viewModelScope.launch {
            _teams.value = usecase.sortByLosses()
        }
    }
}
