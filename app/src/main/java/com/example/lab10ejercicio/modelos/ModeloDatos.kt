package com.example.lab10ejercicio.modelos

data class BookResponse(
    val items: List<Book>
)

data class Book(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val publisher: String?,
    val publishedDate: String?
)