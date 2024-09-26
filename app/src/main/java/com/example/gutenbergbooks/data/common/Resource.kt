package com.example.gutenbergbooks.data.common

import com.example.gutenbergbooks.data.remote.BaseResponse

sealed class Resource<T>(val data: BaseResponse<T>? = null, val message: String? = null) {
    class Success<T>(data: BaseResponse<T>?) : Resource<T>(data)
    class Error<T>(message: String, data: BaseResponse<T>? = null) : Resource<T>(data, message)
    class Loading<T>() : Resource<T>()
}



