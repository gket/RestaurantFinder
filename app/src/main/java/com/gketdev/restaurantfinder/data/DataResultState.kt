package com.gketdev.restaurantfinder.data

sealed class DataResultState<out T> {
    class Success<out T>(val data: T) : DataResultState<T>()
    class Error(
        val error: Throwable? = null,
        val code: Int? = null,
        val message: String? = null
    ) : DataResultState<Nothing>()

}