package com.tomorrow.weatherapp.data.repository.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class BaseRemoteRepository {

    protected inline fun <reified T> parseResponse(response: Response<T>): Flow<T> = flow {
        if (response.isSuccessful) {
            emit(response.body() as T)
        } else {
            throw Exception("An error occurred. Please try again.")
        }
    }
}
