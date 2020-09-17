package com.skanderjabouzi.thescoretest.presentation.players

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skanderjabouzi.thescoretest.data.model.net.Player
import com.skanderjabouzi.thescoretest.data.model.net.Team
import com.skanderjabouzi.thescoretest.domain.listener.usecase.GetTeamPlayersUseCase
import com.skanderjabouzi.thescoretest.domain.listener.usecase.GetTeamsListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamPlayersViewModel @Inject constructor(val usecase: GetTeamPlayersUseCase) : ViewModel() {
    private val _players = MutableLiveData<List<Player>>()

    val players: LiveData<List<Player>>
        get() = _players

    fun searchMovie(teamId: Int) {
        viewModelScope.launch {
            try {
                val listResult = usecase.getTeamPlayers(teamId)
                Log.e("listResult", "$listResult")
                _players.value = listResult
            } catch (e: Exception) {
                Log.e("listResult", "${e.localizedMessage}")
                _players.value = null
            }
        }
    }
}
