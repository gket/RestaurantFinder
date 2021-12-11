package com.gketdev.restaurantfinder.base

import com.gketdev.restaurantfinder.data.DataResultState

abstract class BaseCallMapper {
    protected suspend fun <T : Any> apiCallResponse(call: suspend () -> T): DataResultState<T> {
        return try {
            val response = call()
            DataResultState.Success(response)
        } catch (exception: Throwable) {
            DataResultState.Error(error = exception, message = exception.message.toString())
        }
    }

}