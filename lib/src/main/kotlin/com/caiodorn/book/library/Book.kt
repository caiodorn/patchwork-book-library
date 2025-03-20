package com.caiodorn.book.library

data class Book(
    val title: String?,
    val author: String?,
    val isbn: String?,
    val isReference: Boolean = false,
)
