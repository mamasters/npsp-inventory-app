package com.example.nspsinventoryprogram.di

import android.content.Context
import androidx.room.Room
import com.example.nspsinventoryprogram.data.local.dao.BookDao
import com.example.nspsinventoryprogram.data.local.dao.NpspItemDao
import com.example.nspsinventoryprogram.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app_database"
        )
            .fallbackToDestructiveMigration(true) // Remove this in production
            .build()
    }

    @Provides
    fun provideBookDao(database: AppDatabase): BookDao {
        return database.bookDao()
    }

    @Provides
    fun provideNpspItemDao(database: AppDatabase): NpspItemDao {
        return database.npspItemDao()
    }
}