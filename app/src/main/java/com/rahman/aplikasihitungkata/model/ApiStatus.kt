package com.rahman.aplikasihitungkata.model

data class ApiStatus(
    val status: String,
    val message: String,
    val id: Int? = null,
    val imageUrl: String? = null,
    val imagePath: String? = null,
    val nama: String? = null,
    val genre: String? = null,
    val rilis: String? = null
)