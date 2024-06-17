package com.example.listhotels.util

sealed class Resource<T>(val data: T? = null, val errorType: ErrorType? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(errorType: ErrorType, data: T? = null) : Resource<T>(data, errorType)
}

enum class ErrorType {
    NO_INTERNET,
    NON_200_RESPONSE,
    SERVER_THROWABLE,
}
