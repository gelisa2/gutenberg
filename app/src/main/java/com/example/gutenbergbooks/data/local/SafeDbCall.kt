package com.example.gutenbergbooks.data.local

import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.data.remote.BaseResponse

suspend fun <T> safeDbCall(action: suspend () -> BaseResponse<T>): Resource<T> {
    return try {
        val result = action()
        Resource.Success(result)
    } catch (e: Exception) {
        Resource.Error("Error retrieving data from database")
    }
}