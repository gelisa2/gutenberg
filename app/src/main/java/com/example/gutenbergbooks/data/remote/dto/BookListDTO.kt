package com.example.gutenbergbooks.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gutenbergbooks.data.remote.BaseResponse
import com.example.gutenbergbooks.domain.model.BookListResponse
import com.google.gson.annotations.SerializedName

@Entity(tableName = "books")
data class BookListDTO(
    @PrimaryKey val id: Long,
    val title: String,
    val authors: List<AuthorsDTO>?,
    val subjects: List<String>?,
    @SerializedName("bookshelves")
    val bookShelves: List<String>?,
    val languages: List<String>?,
    val formats: BookFormatDTO?,
    @SerializedName("download_count")
    val downloadCount: Long? = null

)


fun BaseResponse<BookListDTO>.toDomain(): BaseResponse<BookListResponse> {
    return BaseResponse(
        count = this.count,
        next = this.next,
        previous = this.previous,
        results = this.results?.map { BookListResponse(
            id = it.id,
            title = it.title,
            authors = it.authors,
            subjects = it.subjects,
            bookshelves = it.bookShelves,
            languages = it.languages,
            formats = it.formats,
            downloadCount = it.downloadCount
        ) }
    )
}