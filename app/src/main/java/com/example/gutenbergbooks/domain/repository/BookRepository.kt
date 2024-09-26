package com.example.gutenbergbooks.domain.repository

import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.data.remote.BaseResponse
import com.example.gutenbergbooks.data.remote.dto.BookListDTO
import com.example.gutenbergbooks.domain.model.BookListResponse


interface BookRepository {
    suspend fun getBookList(): Resource<BookListResponse>
    suspend fun getBookDetails()
}