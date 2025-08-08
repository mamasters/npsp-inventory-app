package com.example.nspsinventoryprogram

import android.app.Application
import com.example.nspsinventoryprogram.data.local.entity.Book
import com.example.nspsinventoryprogram.data.local.entity.NpspItem
import com.example.nspsinventoryprogram.data.local.repository.BookRepository
import com.example.nspsinventoryprogram.data.local.repository.NpspItemRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var bookRepository: BookRepository
    @Inject
    lateinit var npspItemRepository: NpspItemRepository

    override fun onCreate() {
        super.onCreate()

        // Add test data when app starts
        CoroutineScope(Dispatchers.IO).launch {
            addTestBooks()
            addTestNpspItems()
        }
    }

    private suspend fun addTestBooks() {
        try {
            // Check if books already exist to avoid duplicates
            val existingBooks = bookRepository.getBookCount()

            if (existingBooks == 0) {
                val testBooks = listOf(
                    Book(
                        isbn = "978-0134685991",
                        title = "Effective Java",
                        author = "Joshua Bloch",
                        associatedProgram = "Computer Science",
                        estimatedUnitPrice = 45.99,
                        totalQuantity = 5
                    ),
                    Book(
                        isbn = "978-0135974445",
                        title = "Clean Architecture",
                        author = "Robert C. Martin",
                        associatedProgram = "Software Engineering",
                        estimatedUnitPrice = 52.50,
                        totalQuantity = 3
                    ),
                    Book(
                        isbn = "978-0321356680",
                        title = "Effective C++",
                        author = "Scott Meyers",
                        associatedProgram = "Computer Science",
                        estimatedUnitPrice = 38.75,
                        totalQuantity = 2
                    ),
                    Book(
                        isbn = "978-0596009205",
                        title = "Head First Design Patterns",
                        author = "Eric Freeman",
                        associatedProgram = "Software Engineering",
                        estimatedUnitPrice = 41.20,
                        totalQuantity = 4
                    ),
                    Book(
                        isbn = "978-0132350884",
                        title = "Clean Code",
                        author = "Robert C. Martin",
                        associatedProgram = "Software Engineering",
                        estimatedUnitPrice = 47.80,
                        totalQuantity = 6
                    ),
                    Book(
                        isbn = "978-0134494166",
                        title = "Clean Coder",
                        author = "Robert C. Martin",
                        associatedProgram = "Professional Development",
                        estimatedUnitPrice = 35.99,
                        totalQuantity = 7
                    ),
                    Book(
                        isbn = "978-0321125217",
                        title = "Domain-Driven Design",
                        author = "Eric Evans",
                        associatedProgram = "Software Architecture",
                        estimatedUnitPrice = 55.00,
                        totalQuantity = 2
                    )
                )

                bookRepository.insertBooks(testBooks)
                println("Test books added successfully! Total: ${testBooks.size}")
            } else {
                println("Books already exist in database: $existingBooks books")
            }
        } catch (e: Exception) {
            println("Error adding test books: ${e.message}")
            e.printStackTrace()
        }
    }

    private suspend fun addTestNpspItems() {
        try {
            // Check if NPSP items already exist to avoid duplicates
            val existingNpspItems = npspItemRepository.getNpspItemCount()

            if (existingNpspItems == 0) {
                val testNpspItems = listOf(
                    NpspItem(
                        productNumber = "ABC1234",
                        productName = "Widget",
                        description = "A simple widget",
                        vendorName = "Widget Co.",
                        programTieBack = "Curriculum: PATs and Take Root",
                        estimatedUnitPrice = 10.99,
                        // need = true, attribute removed from class
                        notes = "New in box"
                    ),
                    NpspItem(
                        productNumber = "XYZ5678",
                        productName = "Gadget",
                        description = "A powerful gadget",
                        vendorName = "Gadget Inc.",
                        programTieBack = "Curriculum: PATs and Take Root",
                        estimatedUnitPrice = 19.99,
                        // need = true,
                        notes = "New in box"
                    ),
                    NpspItem(
                        productNumber = "DEF9876",
                        productName = "Thingamajig",
                        description = "A fun toy",
                        vendorName = "Toy Co.",
                        programTieBack = "Lakeshore",
                        estimatedUnitPrice = 5.99,
                       // need = null,
                        notes = null
                    )
                )

                npspItemRepository.insertNpspItems(testNpspItems)
                println("Test NPSP items added successfully! Total: ${testNpspItems.size}")
            } else {
                println("NPSP items already exist in database: $existingNpspItems items")
            }
        } catch (e: Exception) {
            println("Error adding test NPSP items: ${e.message}")
            e.printStackTrace()
        }
    }
}