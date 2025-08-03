package com.example.nspsinventoryprogram.data.local.repository

import com.example.nspsinventoryprogram.data.local.dao.BookDao
import com.example.nspsinventoryprogram.data.local.entity.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

//TODO Add more queries after creating and testing database. Match them with Dao.

@Singleton
class BookRepository @Inject constructor(
    private val bookDao: BookDao
) {
    fun getAllBooks(): Flow<List<Book>> = bookDao.getAllBooks()

    suspend fun getBookCount(): Int = bookDao.getBookCount()

    suspend fun insertBook(book: Book) = bookDao.insertBook(book)

    suspend fun insertBooks(books: List<Book>) = bookDao.insertBooks(books)

    suspend fun updateBook(book: Book) = bookDao.updateBook(book)

    suspend fun deleteBook(book: Book) = bookDao.deleteBook(book)
}