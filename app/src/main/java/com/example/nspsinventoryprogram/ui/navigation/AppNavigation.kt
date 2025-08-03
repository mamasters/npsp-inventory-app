package com.example.nspsinventoryprogram.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nspsinventoryprogram.data.local.repository.BookRepository
import com.example.nspsinventoryprogram.data.local.repository.NpspItemRepository
import com.example.nspsinventoryprogram.ui.screen.AddBookScreen
import com.example.nspsinventoryprogram.ui.screen.AddNpspItemScreen
import com.example.nspsinventoryprogram.ui.screen.BookInventoryScreen
import com.example.nspsinventoryprogram.ui.screen.HomeScreen
import com.example.nspsinventoryprogram.ui.screen.NpspItemInventoryScreen

@Composable
fun AppNavigation(
    bookRepository: BookRepository,
    npspItemRepository: NpspItemRepository,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.HOME
    ) {
        // Home Screen
        composable(NavigationRoutes.HOME) {
            HomeScreen(
                bookRepository = bookRepository,
                npspItemRepository = npspItemRepository,
                onNavigateToBooks = {
                    navController.navigate(NavigationRoutes.BOOK_LIST)
                },
                onNavigateToNpspItems = {
                    navController.navigate(NavigationRoutes.NPSP_ITEM_LIST)
                }
            )
        }
        // Book List Screen
        composable(NavigationRoutes.BOOK_LIST) {
            BookInventoryScreen(
                bookRepository = bookRepository,
                onBookClick = { bookId ->
                    // TODO: Navigate to book detail when you create that screen
                    // navController.navigate("${NavigationRoutes.BOOK_DETAIL}/$bookId")
                },
                onAddBookClick = {
                    navController.navigate(NavigationRoutes.ADD_BOOK)
                },
                onNavigateHome = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }

        // Add Book Screen
        composable(NavigationRoutes.ADD_BOOK) {
            AddBookScreen(
                bookRepository = bookRepository,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // NPSP Item List Screen
        composable(NavigationRoutes.NPSP_ITEM_LIST) {
            NpspItemInventoryScreen(
                npspItemRepository = npspItemRepository,
                onNpspItemClick = { npspItemId ->
                    // TODO: Navigate to NPSP item detail when you create that screen
                    // navController.navigate("${NavigationRoutes.NPSP_ITEM_DETAIL}/$npspItemId")
                },
                onAddNpspItemClick = {
                    navController.navigate(NavigationRoutes.ADD_NPSP_ITEM)
                },
                onNavigateHome = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }

        // Add NPSP Item Screen
        composable(NavigationRoutes.ADD_NPSP_ITEM) {
            AddNpspItemScreen(
                npspItemRepository = npspItemRepository,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}