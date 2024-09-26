package com.example.gutenbergbooks.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthorsDTO(
    val name: String?,
    @SerializedName("birth_year")
    val birthYear: Int?,
    @SerializedName("death_year")
    val deathYear: Int?
): Parcelable