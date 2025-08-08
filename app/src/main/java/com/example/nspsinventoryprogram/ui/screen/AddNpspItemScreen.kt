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
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.nspsinventoryprogram.data.local.entity.NpspItem
import com.example.nspsinventoryprogram.data.local.repository.NpspItemRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNpspItemScreen(
    npspItemRepository: NpspItemRepository,
    onNavigateBack: () -> Unit
) {
    var productNumber by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var vendorName by remember { mutableStateOf("") }
    var programTieBack by remember { mutableStateOf("") }
    var estimatedPrice by remember { mutableStateOf("") }
    var need by remember { mutableStateOf<Boolean?>(null) }
    var notes by remember { mutableStateOf("") }
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
        "General Education",
        "Other"
    )

    // Form validation
    val isFormValid = productNumber.isNotBlank() &&
            productName.isNotBlank() &&
            description.isNotBlank() &&
            vendorName.isNotBlank() &&
            programTieBack.isNotBlank() &&
            estimatedPrice.isNotBlank() &&
            estimatedPrice.toDoubleOrNull() != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add New NPSP Item",
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
                        text = "NPSP Item Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    // Product Number Field
                    OutlinedTextField(
                        value = productNumber,
                        onValueChange = { productNumber = it },
                        label = { Text("Product Number") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("e.g., PN-12345") },
                        isError = productNumber.isBlank() && productNumber.isNotEmpty()
                    )

                    // Product Name Field
                    OutlinedTextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = { Text("Product Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = productName.isBlank() && productName.isNotEmpty()
                    )

                    // Description Field
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        isError = description.isBlank() && description.isNotEmpty()
                    )

                    // Vendor Name Field
                    OutlinedTextField(
                        value = vendorName,
                        onValueChange = { vendorName = it },
                        label = { Text("Vendor Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = vendorName.isBlank() && vendorName.isNotEmpty()
                    )

                    // Program Dropdown
                    ExposedDropdownMenuBox(
                        expanded = programDropdownExpanded,
                        onExpandedChange = { programDropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = programTieBack,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Program Tie Back") },
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
                                        programTieBack = program
                                        programDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Price Field
                    OutlinedTextField(
                        value = estimatedPrice,
                        onValueChange = {
                            // Only allow numbers and decimal point
                            if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                estimatedPrice = it
                            }
                        },
                        label = { Text("Estimated Unit Price") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        leadingIcon = { Text("$") },
                        placeholder = { Text("0.00") },
                        isError = estimatedPrice.isNotBlank() && estimatedPrice.toDoubleOrNull() == null
                    )

                    // Notes Field (Optional)
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notes (Optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        placeholder = { Text("Additional notes or comments...") }
                    )
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
                                val newNpspItem = NpspItem(
                                    productNumber = productNumber.trim(),
                                    productName = productName.trim(),
                                    description = description.trim(),
                                    vendorName = vendorName.trim(),
                                    programTieBack = programTieBack,
                                    estimatedUnitPrice = estimatedPrice.toDouble(),
                                   // need = need, Attribute removed from class
                                    notes = if (notes.isBlank()) null else notes.trim()
                                )

                                npspItemRepository.insertNpspItem(newNpspItem)

                                // Show success message and navigate back
                                snackbarHostState.showSnackbar("NPSP Item added successfully!")
                                onNavigateBack()

                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error adding NPSP item: ${e.message}")
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = isFormValid && !isLoading
                ) {
                    Text(if (isLoading) "Adding..." else "Add Item")
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
                        text = "Please fill in all required fields with valid information",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}