package com.skanderjabouzi.nbateamviewer.presentation.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailsViewModel @Inject constructor(
    val usecase: TeamDetailsUseCase) : ViewModel()
{
    val teamDetails = usecase.teamDetails
    val error = usecase.error

    fun getTeamDetails(id: Int) {
        viewModelScope.launch {
            usecase.getTeamDetails(id)
        }
    }
}