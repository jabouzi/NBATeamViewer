package com.skanderjabouzi.nbateamviewer.presentation.team

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skanderjabouzi.nbateamviewer.data.repository.gateway.TeamDetailsRepository
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamDetailsUseCase
import kotlinx.coroutines.launch

class TeamDetailsViewModel(application: Application) : AndroidViewModel(application) {
    val repository = TeamDetailsRepository(application)
    val usecase = TeamDetailsUseCase(repository)

    val teamDetails = usecase.teamDetails
    val error = usecase.error

    fun getTeamDetails(id: Int) {
        viewModelScope.launch {
            usecase.getTeamDetails(id)
        }
    }
}