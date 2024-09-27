package com.example.gutenbergbooks.data.repository

import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.data.local.dao.BooksDao
import com.example.gutenbergbooks.data.local.safeDbCall
import com.example.gutenbergbooks.data.remote.BaseResponse
import com.example.gutenbergbooks.data.remote.BookApi
import com.example.gutenbergbooks.data.remote.dto.toDomain
import com.example.gutenbergbooks.data.remote.safeApiCall
import com.example.gutenbergbooks.domain.model.BookListResponse
import com.example.gutenbergbooks.domain.model.toEntity
import com.example.gutenbergbooks.domain.repository.BookRepository
import com.example.utils.NetworkUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val bookApi: BookApi,
    private val booksDao: BooksDao,
    private val networkUtils: NetworkUtils
): BookRepository {

    override suspend fun getBookList(page: Int): Resource<BookListResponse> {

        return if (networkUtils.isInternetAvailable()) {
            val apiResponse = safeApiCall {
                bookApi.getBookList(page).toDomain()
            }

            if (apiResponse is Resource.Success) {
                apiResponse.data?.results?.map { it.toEntity() }?.let { booksDao.insertBooks(it) }
            }
            apiResponse
        } else {
            val pageSize = 32
            val offset = (page - 1) * pageSize
            val dbResult = safeDbCall {
                BaseResponse(results = booksDao.getAllBooks(pageSize, offset)).toDomain()
            }
            dbResult
        }

    }
}