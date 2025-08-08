package com.example.nspsinventoryprogram.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "npsp_items")
data class NpspItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productNumber: String,
    val productName: String,
    val description: String,
    val vendorName: String,
    val programTieBack: String,
    val estimatedUnitPrice: Double,
   // val need: Boolean?, removed as no longer needed
    val notes: String?,
    val createdAt: Long = System.currentTimeMillis()
)