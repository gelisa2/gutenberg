package com.example.gutenbergbooks.data.local

import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.data.remote.BaseResponse

suspend fun <T> safeDbCall(action: suspend () -> BaseResponse<T>): Resource<T> {
    return try {
        val result = action()
        if (result.results?.isNotEmpty() == true) {
            Resource.Success(result)
        } else {
            Resource.Error("There is no data in database")
        }
    } catch (e: Exception) {
        Resource.Error("Error retrieving data from database")
    }
}