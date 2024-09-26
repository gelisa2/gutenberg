package com.example.gutenbergbooks.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookFormatDTO(
    @SerializedName("text/html")
    val eBook: String,
    @SerializedName("image/jpeg")
    val bookCover: String
): Parcelable