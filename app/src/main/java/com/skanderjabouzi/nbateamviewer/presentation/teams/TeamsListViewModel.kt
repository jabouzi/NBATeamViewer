package com.skanderjabouzi.nbateamviewer.presentation.teams

import android.util.Log
import androidx.compose.samples.crane.di.DefaultDispatcher
import androidx.lifecycle.*
import com.skanderjabouzi.nbateamviewer.data.model.Team
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TeamsListViewModel @Inject constructor(
    private val usecase: TeamsListUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _teams = MutableStateFlow<List<Team>>(emptyList())
    val teams: StateFlow<List<Team>> = _teams
    val error = usecase.error

    fun getTeams() {
        viewModelScope.launch {
            usecase.getTeams().collect {
                _teams.value = it as List<Team>
                Log.e("####4", "${teams.value}")
            }
        }
    }

    fun sortByName() {
        viewModelScope.launch {
            usecase.sortByName()?.let {
                _teams.value = it
            }
        }
    }

    fun sortByWins() {
        viewModelScope.launch {
            usecase.sortByWins()?.let {
                _teams.value = it
            }
        }
    }

    fun sortByLosses() {
        viewModelScope.launch {
            usecase.sortByLosses()?.let {
                _teams.value = it
            }
        }
    }
}
