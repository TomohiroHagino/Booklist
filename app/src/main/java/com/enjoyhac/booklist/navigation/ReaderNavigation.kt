package com.enjoyhac.booklist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.enjoyhac.booklist.screens.ReaderScreens
import com.enjoyhac.booklist.screens.ReaderSplashScreen
import com.enjoyhac.booklist.screens.details.BookDetailsScreen
import com.enjoyhac.booklist.screens.home.Home
import com.enjoyhac.booklist.screens.login.ReaderLoginScreen
import com.enjoyhac.booklist.screens.search.BookSearchViewModel
import com.enjoyhac.booklist.screens.search.SearchScreen
import com.enjoyhac.booklist.screens.stats.ReaderStatsScreen

@ExperimentalComposeUiApi
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name
    ) {
        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }

        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name) {
            Home(navController = navController)
        }

        composable(ReaderScreens.SearchScreen.name) {
            val viewModel = hiltViewModel<BookSearchViewModel>()
            SearchScreen(navController = navController, viewModel)
        }

        composable(ReaderScreens.ReaderStatsScreen.name) {
            ReaderStatsScreen(navController = navController)
        }

        val detailName = ReaderScreens.DetailScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId") {
            type = NavType.StringType
            })) { backStackEntry ->
                backStackEntry.arguments?.getString("bookId").let {
                val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                BookDetailsScreen(navController = navController, bookId = bookId)
            }
        }
    }
}