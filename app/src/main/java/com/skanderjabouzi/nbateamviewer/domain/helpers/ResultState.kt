package com.skanderjabouzi.nbateamviewer.domain.helpers

sealed class ResultState {
    data class Success(val data: Any) : ResultState()
    data class Error(val error: String) : ResultState()
}