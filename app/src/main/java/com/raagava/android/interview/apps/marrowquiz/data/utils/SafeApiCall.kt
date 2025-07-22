package com.raagava.android.interview.apps.marrowquiz.data.utils

import com.raagava.android.interview.apps.marrowquiz.utils.DataError
import com.raagava.android.interview.apps.marrowquiz.utils.DataResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): DataResponse<T> {
    return withContext(dispatcher) {
        try {
            DataResponse.Success(apiCall())
        } catch (e: HttpException) {
            DataResponse.Error(
                DataError.HttpError(
                    code = e.code(),
                    message = e.message()
                )
            )
        } catch (e: Exception) {
            DataResponse.Error(
                DataError.RequestError(
                    message = e.message ?: "Something went wrong"
                )
            )
        }
    }
}