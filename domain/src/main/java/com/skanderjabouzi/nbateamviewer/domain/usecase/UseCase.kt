package com.skanderjabouzi.nbateamviewer.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.skanderjabouzi.nbateamviewer.domain.model.Player
import com.skanderjabouzi.nbateamviewer.domain.model.Team

open class UseCase {
    val playersList = MutableLiveData<List<Player>>()
    val teamsList = MutableLiveData<List<Team>>()
    val error = MutableLiveData<String>()

    fun handleCharactersResult(resultState: ResultState) {
        when (resultState) {
            is ResultState.HttpErrors.ResourceForbidden -> error.postValue(resultState.exception)
            is ResultState.HttpErrors.ResourceNotFound -> error.postValue(resultState.exception)
            is ResultState.HttpErrors.InternalServerError -> error.postValue(resultState.exception)
            is ResultState.HttpErrors.BadGateWay -> error.postValue(resultState.exception)
            is ResultState.HttpErrors.ResourceRemoved -> error.postValue(resultState.exception)
            is ResultState.HttpErrors.RemovedResourceFound -> error.postValue(resultState.exception)
            is ResultState.Error -> error.postValue(resultState.error)
            is ResultState.NetworkException -> error.postValue(resultState.error)
            else -> error.postValue("")
        }
    }

    fun validateResponse(responseCode: Int, responseMessage: String): ResultState {
        return when (responseCode) {
            403 -> ResultState.HttpErrors.ResourceForbidden(responseMessage)
            404 -> ResultState.HttpErrors.ResourceNotFound(responseMessage)
            500 -> ResultState.HttpErrors.InternalServerError(responseMessage)
            502 -> ResultState.HttpErrors.BadGateWay(responseMessage)
            301 -> ResultState.HttpErrors.ResourceRemoved(responseMessage)
            302 -> ResultState.HttpErrors.RemovedResourceFound(responseMessage)
            else -> ResultState.Error(responseMessage)
        }
    }
}