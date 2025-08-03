package com.example.nspsinventoryprogram.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.nspsinventoryprogram.data.local.entity.Book
import com.example.nspsinventoryprogram.data.local.repository.BookRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    bookRepository: BookRepository,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var estimatedPrice by remember { mutableStateOf("") }
    var associatedProgram by remember { mutableStateOf("") }
    var programDropdownExpanded by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // TODO: This is data created for testing
    //  Replace with actual program options
    val programOptions = listOf(
        "Computer Science",
        "Business Administration",
        "Engineering",
        "Mathematics",
        "English Literature",
        "Biology",
        "Chemistry",
        "Physics",
        "History",
        "Psychology",
        "Other"
    )

    // Form validation
    val isFormValid = title.isNotBlank() &&
            author.isNotBlank() &&
            isbn.isNotBlank() &&
            estimatedPrice.isNotBlank() &&
            associatedProgram.isNotBlank() &&
            estimatedPrice.toDoubleOrNull() != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add New Book",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Book Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    // Title Field
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Book Title") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = title.isBlank() && title.isNotEmpty()
                    )

                    // Author Field
                    OutlinedTextField(
                        value = author,
                        onValueChange = { author = it },
                        label = { Text("Author") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = author.isBlank() && author.isNotEmpty()
                    )

                    // ISBN Field
                    OutlinedTextField(
                        value = isbn,
                        onValueChange = { isbn = it },
                        label = { Text("ISBN") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("e.g., 978-0123456789") },
                        isError = isbn.isBlank() && isbn.isNotEmpty()
                    )

                    // Price Field
                    OutlinedTextField(
                        value = estimatedPrice,
                        onValueChange = {
                            // Only allow numbers and decimal point
                            if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                estimatedPrice = it
                            }
                        },
                        label = { Text("Estimated Price") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        leadingIcon = { Text("$") },
                        placeholder = { Text("0.00") },
                        isError = estimatedPrice.isNotBlank() && estimatedPrice.toDoubleOrNull() == null
                    )

                    // Program Dropdown
                    ExposedDropdownMenuBox(
                        expanded = programDropdownExpanded,
                        onExpandedChange = { programDropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = associatedProgram,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Associated Program") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = programDropdownExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = programDropdownExpanded,
                            onDismissRequest = { programDropdownExpanded = false }
                        ) {
                            programOptions.forEach { program ->
                                DropdownMenuItem(
                                    text = { Text(program) },
                                    onClick = {
                                        associatedProgram = program
                                        programDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            isLoading = true
                            try {
                                val newBook = Book(
                                    title = title.trim(),
                                    author = author.trim(),
                                    isbn = isbn.trim(),
                                    estimatedUnitPrice = estimatedPrice.toDouble(),
                                    associatedProgram = associatedProgram
                                )

                                bookRepository.insertBook(newBook)

                                // Show success message and navigate back
                                snackbarHostState.showSnackbar("Book added successfully!")
                                onNavigateBack()

                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error adding book: ${e.message}")
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = isFormValid && !isLoading
                ) {
                    Text(if (isLoading) "Adding..." else "Add Book")
                }
            }

            // Form validation hints
            if (!isFormValid) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = "Please fill in all fields with valid information",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}