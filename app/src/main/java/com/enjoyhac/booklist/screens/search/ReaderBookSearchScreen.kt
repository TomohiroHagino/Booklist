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

@Preview
@Composable
fun SearchScreen(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Books",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController,
        )
    }) {
        // これも引数。ラムダなのでこのように渡している。
        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
    }
}