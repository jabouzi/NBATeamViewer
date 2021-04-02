package com.skanderjabouzi.nbateamviewer.domain.usecase

sealed class ResultState {
    data class Success(val data: Any) : ResultState()
    data class Error(val error: String) : ResultState()
}