package com.enjoyhac.booklist.navigation

import android.util.Log
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
import com.enjoyhac.booklist.screens.home.HomeScreenViewModel
import com.enjoyhac.booklist.screens.login.ReaderLoginScreen
import com.enjoyhac.booklist.screens.search.BookSearchViewModel
import com.enjoyhac.booklist.screens.search.SearchScreen
import com.enjoyhac.booklist.screens.stats.ReaderStatsScreen
import com.enjoyhac.booklist.screens.update.BookUpdateScreen

@ExperimentalComposeUiApi
@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = ReaderScreens.SplashScreen.name ){

        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderStatsScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ReaderStatsScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(ReaderScreens.ReaderHomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            Home(navController = navController, viewModel = homeViewModel)
        }

        composable(ReaderScreens.SearchScreen.name) {
            val searchViewModel = hiltViewModel<BookSearchViewModel>()

            SearchScreen(navController = navController, viewModel = searchViewModel)
        }



        val detailName = ReaderScreens.DetailScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {

                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }

        val updateName = ReaderScreens.UpdateScreen.name
        composable("$updateName/{bookItemId}",
            arguments = listOf(navArgument("bookItemId") {
                type = NavType.StringType
            })) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(navController = navController, bookItemId = it.toString())
            }
        }
    }
}