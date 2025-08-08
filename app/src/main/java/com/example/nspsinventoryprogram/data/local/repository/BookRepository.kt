package com.example.nspsinventoryprogram.data.local.repository

import com.example.nspsinventoryprogram.data.local.dao.BookDao
import com.example.nspsinventoryprogram.data.local.entity.Book
import com.example.nspsinventoryprogram.ui.screen.BookSortOption
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(
    private val bookDao: BookDao
) {
    fun getAllBooksSorted(sortOption: BookSortOption): Flow<List<Book>> {
        return when (sortOption) {
            BookSortOption.TITLE_ASC -> bookDao.getAllBooksSorted("title", true)
            BookSortOption.TITLE_DESC -> bookDao.getAllBooksSorted("title", false)
            BookSortOption.AUTHOR_ASC -> bookDao.getAllBooksSorted("author", true)
            BookSortOption.AUTHOR_DESC -> bookDao.getAllBooksSorted("author", false)
            BookSortOption.PROGRAM_ASC -> bookDao.getAllBooksSorted("program", true)
            BookSortOption.PROGRAM_DESC -> bookDao.getAllBooksSorted("program", false)
            BookSortOption.PRICE_HIGH -> bookDao.getAllBooksSorted("price", false)
            BookSortOption.PRICE_LOW -> bookDao.getAllBooksSorted("price", true)
            BookSortOption.QUANTITY_HIGH -> bookDao.getAllBooksSorted("quantity", false)
            BookSortOption.QUANTITY_LOW -> bookDao.getAllBooksSorted("quantity", true)
            BookSortOption.DATE_NEWEST -> bookDao.getAllBooksSorted("date", false)
            BookSortOption.DATE_OLDEST -> bookDao.getAllBooksSorted("date", true)
        }
    }
    fun getAllBooks(): Flow<List<Book>> = bookDao.getAllBooks()

    suspend fun getBookCount(): Int = bookDao.getBookCount()

    fun getBooksWithTotalQuantity(): Flow<List<Book>> = bookDao.getBooksWithTotalQuantity()

    suspend fun insertBook(book: Book) = bookDao.insertBook(book)

    suspend fun insertBooks(books: List<Book>) = bookDao.insertBooks(books)

    suspend fun updateBook(book: Book) = bookDao.updateBook(book)

    suspend fun deleteBook(book: Book) = bookDao.deleteBook(book)
}