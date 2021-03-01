package com.skanderjabouzi.nbateamviewer.presentation.teams

import android.util.Log
import androidx.lifecycle.*
import com.skanderjabouzi.nbateamviewer.data.model.net.Team
import com.skanderjabouzi.nbateamviewer.domain.listener.usecase.GetTeamsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TeamsListViewModel @Inject constructor(val usecase: GetTeamsListUseCase) : ViewModel() {
    private val _teams = MutableLiveData<List<Team>>()

    val teams: LiveData<List<Team>>
        get() = _teams

    fun getTeams() {
        viewModelScope.launch {
            try {
                _teams.value = usecase.getTeams()
//                _teams.value = liveData {
//                    emit(usecase.getTeams())
//                }
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
