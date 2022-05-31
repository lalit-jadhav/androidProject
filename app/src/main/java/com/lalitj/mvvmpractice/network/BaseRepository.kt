package com.lalitj.mvvmpractice.network


import com.lalitj.mvvmpractice.AppConstants
import retrofit2.Response


abstract class BaseRepository {

    protected suspend fun <T : Any> makeApiCall(
        call: suspend () -> Response<T>
    ): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
            }
            return error(response.code(), response.message())
        } catch (e: Exception) {
            return error(message = e.message ?: e.toString())
        }
    }

    private fun <T : Any> error(
        errorCode: Int = AppConstants.HttpResCodes.SOMETHING_WENT_WRONG,
        message: String
    ): Result<T> {
        // return Result.Error("Network call has failed for a following reason: $message")
        return Result.Error(
            message = fetchErrorMessage(
                errorCode = errorCode,
                errorMessage = message
            )
        )

    }


    private fun fetchErrorMessage(errorCode: Int, errorMessage: String): String = when (errorCode) {
        AppConstants.HttpResCodes.STATUS_NO_INTERNET -> "Please check your internet and try again"
        AppConstants.HttpResCodes.SOMETHING_WENT_WRONG -> "Something went wrong."
        AppConstants.HttpResCodes.STATUS_NO_ITEMS_FOUND -> "No data found"
        AppConstants.HttpResCodes.STATUS_NOT_FOUND -> "Something went wrong."
        AppConstants.HttpResCodes.STATUS_UNAUTHORIZED -> "Something went wrong."
        AppConstants.HttpResCodes.STATUS_SERVER_ERROR -> "Something went wrong."
        AppConstants.HttpResCodes.STATUS_INTERNAL_ERROR -> "Internal Server Error"
        AppConstants.HttpResCodes.STATUS_CUSTOM -> errorMessage
        else -> "Something went wrong."
    }


}
