package com.skanderjabouzi.nbateamviewer.presentation.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skanderjabouzi.nbateamviewer.data.model.TeamDetails
import com.skanderjabouzi.nbateamviewer.domain.usecase.TeamDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailsViewModel @Inject constructor(
    val usecase: TeamDetailsUseCase) : ViewModel()
{
    private var _teamDetails = MutableLiveData<TeamDetails>()
    val teamDetails:  LiveData<TeamDetails>
        get() = _teamDetails
    val error = usecase.error

    fun getTeamDetails(id: Int) {
        viewModelScope.launch {
            _teamDetails.value = usecase.getTeamDetails(id)
        }
    }
}