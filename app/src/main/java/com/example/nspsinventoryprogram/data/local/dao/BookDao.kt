package com.example.nspsinventoryprogram.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nspsinventoryprogram.data.local.entity.Book
import kotlinx.coroutines.flow.Flow

// TODO Add more queries and CRUD operations.
@Dao
interface BookDao {

    // Get all book queries
    @Query("SELECT * FROM books ORDER BY title ASC")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT COUNT(*) FROM books")
    suspend fun getBookCount(): Int

    @Query("SELECT * FROM books ORDER BY " +
            "CASE WHEN :sortBy = 'title' AND :ascending = 1 THEN title END ASC, " +
            "CASE WHEN :sortBy = 'title' AND :ascending = 0 THEN title END DESC, " +
            "CASE WHEN :sortBy = 'author' AND :ascending = 1 THEN author END ASC, " +
            "CASE WHEN :sortBy = 'author' AND :ascending = 0 THEN author END DESC, " +
            "CASE WHEN :sortBy = 'program' AND :ascending = 1 THEN associatedProgram END ASC, " +
            "CASE WHEN :sortBy = 'program' AND :ascending = 0 THEN associatedProgram END DESC, " +
            "CASE WHEN :sortBy = 'price' AND :ascending = 1 THEN estimatedUnitPrice END ASC, " +
            "CASE WHEN :sortBy = 'price' AND :ascending = 0 THEN estimatedUnitPrice END DESC, " +
            "CASE WHEN :sortBy = 'quantity' AND :ascending = 1 THEN totalQuantity END ASC, " +
            "CASE WHEN :sortBy = 'quantity' AND :ascending = 0 THEN totalQuantity END DESC, " +
            "CASE WHEN :sortBy = 'date' AND :ascending = 1 THEN createdAt END ASC, " +
            "CASE WHEN :sortBy = 'date' AND :ascending = 0 THEN createdAt END DESC")
    fun getAllBooksSorted(sortBy: String, ascending: Boolean): Flow<List<Book>>

    @Query("""
        SELECT 
            MIN(id) as id,
            title, 
            author, 
            isbn,
            associatedProgram,
            MAX(createdAt) as createdAt,
            MIN(estimatedUnitPrice) as estimatedUnitPrice,
            SUM(totalQuantity) as totalQuantity
        FROM books 
        GROUP BY COALESCE(isbn, title || '|' || author)
        ORDER BY title
    """)
        fun getBooksWithTotalQuantity(): Flow<List<Book>>

    // CRUD operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<Book>)

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}