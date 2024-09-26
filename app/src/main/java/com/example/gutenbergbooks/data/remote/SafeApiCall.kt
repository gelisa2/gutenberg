package com.example.gutenbergbooks.data.remote

import com.example.gutenbergbooks.data.common.Resource
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(call: suspend () -> BaseResponse<T>): Resource<T> {

    return try {
        val result = call()
        if (result.results == null || result.results?.isEmpty() == true) {
            return Resource.Error("no data found")
        }
        Resource.Success(data = result)
    }catch (e: IOException) {
        Resource.Error("No internet connection")
    }catch (e: HttpException) {
        Resource.Error("Server error: ${e.code()} - ${e.message()}")
    }catch (e: SocketTimeoutException) {
        Resource.Error("Unexpected error: ${e.message}")
    }
}