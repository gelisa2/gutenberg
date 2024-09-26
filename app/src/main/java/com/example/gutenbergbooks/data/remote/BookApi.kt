package com.example.gutenbergbooks.data.remote

import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.data.remote.dto.BookListDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("/books")
    suspend fun getBookList(@Query("page") page: Int): BaseResponse<BookListDTO>
}