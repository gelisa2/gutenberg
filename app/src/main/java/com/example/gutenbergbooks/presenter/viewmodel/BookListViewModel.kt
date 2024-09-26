package com.example.gutenbergbooks.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gutenbergbooks.data.common.Resource
import com.example.gutenbergbooks.domain.model.BookListResponse
import com.example.gutenbergbooks.domain.use_case.BookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookListUseCase: BookListUseCase
): ViewModel() {

//    private val _bookListFlow: MutableStateFlow<Resource<BookListResponse>> = MutableStateFlow(Resource.Loading())
//    val bookListFlow: StateFlow<Resource<BookListResponse>> = _bookListFlow
    private val _bookListFlow: MutableSharedFlow<Resource<BookListResponse>> = MutableSharedFlow()
    val bookListFlow: SharedFlow<Resource<BookListResponse>> = _bookListFlow

    init {
        getBookList()
    }

     private fun getBookList() {
        viewModelScope.launch {
            bookListUseCase().onEach {result ->
                when (result) {
                    is Resource.Loading -> {
                        _bookListFlow.emit(Resource.Loading())
                    }
                    is Resource.Success -> {
                        _bookListFlow.emit(result)
                    }
                    is Resource.Error -> {
                        _bookListFlow.emit(Resource.Error(result.message.toString()))
                    }
                }

            }.launchIn(this)
        }
    }

}