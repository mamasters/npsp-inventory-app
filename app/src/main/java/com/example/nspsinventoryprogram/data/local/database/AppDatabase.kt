package com.example.nspsinventoryprogram.data.local.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nspsinventoryprogram.data.local.dao.BookDao
import com.example.nspsinventoryprogram.data.local.dao.NpspItemDao
import com.example.nspsinventoryprogram.data.local.entity.Book
import com.example.nspsinventoryprogram.data.local.entity.NpspItem

@Database (
    entities = [Book::class, NpspItem::class],
    version = 4,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun npspItemDao(): NpspItemDao
}