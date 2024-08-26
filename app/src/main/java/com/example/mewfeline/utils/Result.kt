package com.example.mewfeline.utils

sealed class Resource<out T>(val data: T? = null, val message: String? = null){

    class Success<T>(data: T?) : Resource<T>(data = data)

    class Error(message: String?) : Resource<Nothing>(message = message)
}