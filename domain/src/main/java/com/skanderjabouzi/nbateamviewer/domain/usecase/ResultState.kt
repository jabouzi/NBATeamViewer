package com.skanderjabouzi.nbateamviewer.domain.usecase

sealed class ResultState {
    data class Success(val data: Any) : ResultState()
    object InvalidData : ResultState()
    data class Error(val error: String) : ResultState()
    data class NetworkException(val error: String) : ResultState()
    sealed class HttpErrors : ResultState() {
        data class ResourceForbidden(val exception: String) : HttpErrors()
        data class ResourceNotFound(val exception: String) : HttpErrors()
        data class InternalServerError(val exception: String) : HttpErrors()
        data class BadGateWay(val exception: String) : HttpErrors()
        data class ResourceRemoved(val exception: String) : HttpErrors()
        data class RemovedResourceFound(val exception: String) : HttpErrors()
    }
}