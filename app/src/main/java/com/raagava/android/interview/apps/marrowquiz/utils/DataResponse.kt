package com.raagava.android.interview.apps.marrowquiz.utils

sealed class DataResponse<out T> {
    data class Success<T>(val data: T) : DataResponse<T>()
    data class Error(val error: DataError) : DataResponse<Nothing>()
    object Loading : DataResponse<Nothing>()
}

sealed class DataError {
    data class HttpError(val code: Int, val message: String) : DataError()
    data class RequestError(val message: String) : DataError()
}