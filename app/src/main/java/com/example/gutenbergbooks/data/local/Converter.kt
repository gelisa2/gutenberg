package com.example.gutenbergbooks.data.local

import androidx.room.TypeConverter
import com.example.gutenbergbooks.data.remote.BaseResponse
import com.example.gutenbergbooks.data.remote.dto.AuthorsDTO
import com.example.gutenbergbooks.data.remote.dto.BookFormatDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    private val gson = Gson()

    @TypeConverter
    fun fromAuthorsDTOList(authors: List<AuthorsDTO>?): String? {
        return gson.toJson(authors)
    }

    @TypeConverter
    fun toAuthorsDTOList(authorsString: String?): List<AuthorsDTO>? {
        val listType = object : TypeToken<List<AuthorsDTO>>() {}.type
        return gson.fromJson(authorsString, listType)
    }

    @TypeConverter
    fun fromStringList(stringList: List<String>?): String? {
        return gson.toJson(stringList)
    }

    @TypeConverter
    fun toStringList(stringListString: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(stringListString, listType)
    }

    @TypeConverter
    fun fromBookFormatDTO(format: BookFormatDTO?): String? {
        return gson.toJson(format)
    }

    @TypeConverter
    fun toBookFormatDTO(formatString: String?): BookFormatDTO? {
        return gson.fromJson(formatString, BookFormatDTO::class.java)
    }
}