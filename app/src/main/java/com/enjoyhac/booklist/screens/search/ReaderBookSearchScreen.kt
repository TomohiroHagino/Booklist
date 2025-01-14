package com.enjoyhac.booklist.screens.search

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.enjoyhac.booklist.components.ReaderAppBar
import com.enjoyhac.booklist.screens.ReaderScreens

@Composable
fun SearchScreen(navController: NavController) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Books",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController,
        ) { navController.navigate(ReaderScreens.ReaderHomeScreen.name) }
    }) {

    }
}