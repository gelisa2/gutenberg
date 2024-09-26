package com.example.gutenbergbooks.data.remote

data class BaseResponse<T>(
    var count: Long? = null,
    var next: String? = null,
    var previous: String? = null,
    var results: List<T>? = null
)