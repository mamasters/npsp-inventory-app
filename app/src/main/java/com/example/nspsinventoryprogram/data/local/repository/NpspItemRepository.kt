package com.example.nspsinventoryprogram.data.local.repository

import com.example.nspsinventoryprogram.data.local.dao.NpspItemDao
import com.example.nspsinventoryprogram.data.local.entity.NpspItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

//TODO Add more queries after creating and testing database. Match them with Dao.

@Singleton
class NpspItemRepository @Inject constructor(
    private val npspItemDao: NpspItemDao
) {
    // Get all NPSP items
    fun getAllNpspItems(): Flow<List<NpspItem>> = npspItemDao.getAllNpspItems()

    // Get NPSP items by product name
    fun getNpspItemsByProductName(): Flow<List<NpspItem>> = npspItemDao.getNpspItemsByProductName()

    // Get NPSP item count
    suspend fun getNpspItemCount(): Int = npspItemDao.getNpspItemCount()

    // Insert operations
    suspend fun insertNpspItem(npspItem: NpspItem) = npspItemDao.insertNpspItem(npspItem)

    suspend fun insertNpspItems(npspItems: List<NpspItem>) = npspItemDao.insertNpspItems(npspItems)

    // Update operation
    suspend fun updateNpspItem(npspItem: NpspItem) = npspItemDao.updateNpspItem(npspItem)

    // Delete operation
    suspend fun deleteNpspItem(npspItem: NpspItem) = npspItemDao.deleteNpspItem(npspItem)
}