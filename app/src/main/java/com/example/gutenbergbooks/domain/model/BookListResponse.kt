package com.example.gutenbergbooks.domain.model

import android.os.Parcelable
import com.example.gutenbergbooks.data.remote.dto.AuthorsDTO
import com.example.gutenbergbooks.data.remote.dto.BookFormatDTO
import com.example.gutenbergbooks.data.remote.dto.BookListDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookListResponse(
    var id: Long,
    val title: String,
    val authors: List<AuthorsDTO>?,
    val subjects: List<String>?,
    val bookshelves: List<String>?,
    val languages: List<String>?,
    val formats: BookFormatDTO?,
    val downloadCount: Long?
): Parcelable

fun BookListResponse.toEntity(): BookListDTO {
    return BookListDTO(
        id = id,
        title = title,
        authors = authors,
        subjects = subjects,
        bookShelves = bookshelves,
        languages = languages,
        formats = formats,
        downloadCount = downloadCount
    )
}
