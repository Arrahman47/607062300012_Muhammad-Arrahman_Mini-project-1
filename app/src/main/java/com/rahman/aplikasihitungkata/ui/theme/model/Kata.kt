package com.rahman.aplikasihitungkata.ui.theme.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kata")
data class Kata(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nama: String,
    val genre: String,
    val rilis: String,
)