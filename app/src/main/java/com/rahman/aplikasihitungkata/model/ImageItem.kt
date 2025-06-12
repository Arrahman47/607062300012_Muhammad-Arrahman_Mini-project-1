package com.rahman.aplikasihitungkata.model

data class ImageItem(
    val id: Int,
    val imagepath: String,
    val nama: String?,
    val genre: String?,
    val rilis: String?,
    val mimetype: String?,
    val upload_date: String,
    val imageUrl: String? = null
)