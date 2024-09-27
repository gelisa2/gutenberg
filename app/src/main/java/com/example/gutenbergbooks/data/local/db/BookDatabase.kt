package com.example.gutenbergbooks.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.gutenbergbooks.data.local.Converter
import com.example.gutenbergbooks.data.local.dao.BooksDao
import com.example.gutenbergbooks.data.remote.dto.BookListDTO

@Database(entities = [BookListDTO::class], version = 3, exportSchema = false)
@TypeConverters(Converter::class)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookDao(): BooksDao
}