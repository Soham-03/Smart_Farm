package com.example.smartfarm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Note(
    val image: String?,
    val text: String,
    val timestamp: String = System.currentTimeMillis().toString()
)
