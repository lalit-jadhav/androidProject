package com.lalitj.mvvmpractice.network

sealed class Result<out T : Any> {

    data class Loading(val isLoading: Boolean = true) : Result<Nothing>()

    data class Success<out T : Any>(val data: T) : Result<T>()

//    data class Action<out T : Any>(val position : Int = 0, val action : LeadAction, val data : T) : Result<T>()

    data class Error(val message: String? = null, val errorStatus: Boolean = true) :
        Result<Nothing>()

    data class Retry(val isRetry: Boolean = true) : Result<Nothing>()

    data class Empty(val message: String? = null) : Result<Nothing>()

    data class SessionExpired(val message: String? = null) : Result<Nothing>()

    data class ActivityResult<out T : Any>(val requestCode: Int, val resultCode: Int, val data: T) :
        Result<T>()
}