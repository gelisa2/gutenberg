package com.example.gutenbergbooks.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gutenbergbooks.data.remote.BaseResponse
import com.example.gutenbergbooks.data.remote.dto.BookListDTO

@Dao
interface BooksDao {

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BookListDTO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBooks(data: List<BookListDTO>)

}