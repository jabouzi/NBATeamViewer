package com.skanderjabouzi.nbateamviewer.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.skanderjabouzi.nbateamviewer.data.model.Player
import com.skanderjabouzi.nbateamviewer.data.model.Team
import retrofit2.Response
import java.io.IOException

open class UseCase {
    val error = MutableLiveData<String>()

    fun getSortBy(sortType: SortType): SortType {
        if (sortType == SortType.ASCENDING) {
            return SortType.DESCENDING
        } else {
            return SortType.ASCENDING
        }
    }

     fun getRequestFromApi(response: Response<*>): ResultState? {
        return try {
            if (response.isSuccessful) {
                response.body()?.let { ResultState.Success(it) }
            } else {
                ResultState.Error(response.message())
            }
        } catch (error: IOException) {
            error.message?.let { ResultState.Error(it) }
        }
    }
}