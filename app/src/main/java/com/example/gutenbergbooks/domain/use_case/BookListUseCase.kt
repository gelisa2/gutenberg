package com.example.gutenbergbooks.domain.use_case

import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.domain.repository.BookRepository
import com.example.utils.NetworkUtils
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookListUseCase @Inject constructor(
    private val repository: BookRepository
) {

    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
//        if (!networkUtils.isInternetAvailable()) {
//            val dbData = repository.getBookListFromDb
//            if (dbdata != null) {
//                emit(Resource.Success(dbdata))
//            } else {
//            emit(Resource.Error("there is no data in db"))
//        }
//            return@flow
//        }

        emit(Resource.Success(repository.getBookList().data))

    }

}