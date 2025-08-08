package com.example.nspsinventoryprogram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val isbn: String,
    val title: String,
    val author: String,
    val associatedProgram: String,
    val estimatedUnitPrice: Double,
    val totalQuantity: Int,
    val createdAt: Long = System.currentTimeMillis()
)