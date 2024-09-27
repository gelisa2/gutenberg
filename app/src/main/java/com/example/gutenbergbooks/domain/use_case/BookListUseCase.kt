package com.example.gutenbergbooks.domain.use_case

import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.domain.repository.BookRepository
import com.example.utils.NetworkUtils
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookListUseCase @Inject constructor(
    private val repository: BookRepository
) {

    suspend operator fun invoke(page: Int) = flow {
        emit(Resource.Loading())

        val data = repository.getBookList(page)

        if (data.data?.results?.isNotEmpty() == true) {
            emit(Resource.Success(data.data))
        } else {
            emit(Resource.Error(data.message.toString()))
        }
    }

}