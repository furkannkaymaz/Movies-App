package com.furkan.beinConnectMovies.data.remote.dto

open class Callback<T>(
    val data: T? = null,
    val error: T? = null,
) {
    class Success<T>(_data: T) : Callback<T>(data = _data)
    class Failure<T>(_message: T) : Callback<T>(error = _message)
}
