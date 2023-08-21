package com.example.movieapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieReview(
    val id: String?,
    val author: String?,
    val authorDetails: AuthorDetails?,
    val content: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val url: String?,
) : Parcelable
