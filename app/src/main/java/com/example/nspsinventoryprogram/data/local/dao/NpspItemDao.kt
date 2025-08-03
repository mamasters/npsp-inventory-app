package com.example.nspsinventoryprogram.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nspsinventoryprogram.data.local.entity.NpspItem
import kotlinx.coroutines.flow.Flow

// TODO Add more queries and CRUD operations.
@Dao
interface NpspItemDao {

    // Get all queries
    @Query("SELECT * FROM npsp_items ORDER BY productNumber ASC")
    fun getAllNpspItems(): Flow<List<NpspItem>>

    @Query("SELECT * FROM npsp_items ORDER BY productName ASC")
    fun getNpspItemsByProductName(): Flow<List<NpspItem>>

    @Query("SELECT COUNT(*) FROM npsp_items")
    suspend fun getNpspItemCount(): Int

    // CRUD operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNpspItem(npspItem: NpspItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNpspItems(npspItems: List<NpspItem>)

    @Update
    suspend fun updateNpspItem(npspItem: NpspItem)

    @Delete
    suspend fun deleteNpspItem(npspItem: NpspItem)

}