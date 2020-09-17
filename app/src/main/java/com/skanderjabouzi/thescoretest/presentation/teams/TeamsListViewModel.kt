package com.skanderjabouzi.thescoretest.presentation.teams

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skanderjabouzi.thescoretest.data.model.net.Team
import com.skanderjabouzi.thescoretest.domain.listener.usecase.GetTeamsListUseCase
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
                val listResult = usecase.getTeams()
                _teams.value = listResult
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _teams.value = null
            }
        }
    }
}
