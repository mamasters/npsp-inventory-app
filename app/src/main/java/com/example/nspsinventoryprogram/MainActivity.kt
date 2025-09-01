package com.example.nspsinventoryprogram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.nspsinventoryprogram.data.local.repository.BookRepository
import com.example.nspsinventoryprogram.data.local.repository.NpspItemRepository
import com.example.nspsinventoryprogram.ui.navigation.AppNavigation
import com.example.nspsinventoryprogram.ui.theme.NSPSInventoryProgramTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var npspItemRepository: NpspItemRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NSPSInventoryProgramTheme {
                AppNavigation(
                    bookRepository = bookRepository,
                    npspItemRepository = npspItemRepository)
            }
        }
    }
}