package com.example.gutenbergbooks.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gutenbergbooks.data.remote.BaseResponse
import com.example.gutenbergbooks.data.remote.dto.BookListDTO

@Dao
interface BooksDao {

    @Query("SELECT * FROM books LIMIT :limit OFFSET :offset")
    suspend fun getAllBooks(limit: Int, offset: Int): List<BookListDTO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBooks(data: List<BookListDTO>)

}