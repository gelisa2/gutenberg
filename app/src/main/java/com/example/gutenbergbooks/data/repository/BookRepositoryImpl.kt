package com.example.gutenbergbooks.data.repository

import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.data.local.dao.BooksDao
import com.example.gutenbergbooks.data.local.safeDbCall
import com.example.gutenbergbooks.data.remote.BaseResponse
import com.example.gutenbergbooks.data.remote.BookApi
import com.example.gutenbergbooks.data.remote.dto.BookListDTO
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

    override suspend fun getBookList(): Resource<BookListResponse> {

        return if (networkUtils.isInternetAvailable()) {
            val apiResponse = safeApiCall {
                bookApi.getBookList(5).toDomain()
            }

            if (apiResponse is Resource.Success) {
                apiResponse.data?.results?.map { it.toEntity() }?.let { booksDao.insertBooks(it) }
            }
            apiResponse
        } else {
            val dbResult = safeDbCall {
                BaseResponse(results = booksDao.getAllBooks()).toDomain()
//                booksDao.getAllBooks().toDomain()
            }
            return dbResult
//            val cachedData = booksDao.getAllBooks()
//            if (cachedData.isNotEmpty()) {
//                return cachedData
////                Resource.Success(BaseResponse(
////                    count = cachedData.size.toLong(),
////                    results = cachedData.map { it.toDomain() }.toCollection(ArrayList())
////                ))
//            }
//            Resource.Error("")
        }

    }

    override suspend fun getBookDetails() {
    }
}