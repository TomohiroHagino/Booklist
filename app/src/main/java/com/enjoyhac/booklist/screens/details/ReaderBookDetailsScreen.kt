package com.enjoyhac.booklist.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.enjoyhac.booklist.components.ReaderAppBar
import com.enjoyhac.booklist.navigation.ReaderNavigation
import com.enjoyhac.booklist.screens.ReaderScreens

@Composable
fun BookDetailsScreen(navController: NavController, bookId: String) {

    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Book Details",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            ) { navController.navigate(ReaderScreens.ReaderHomeScreen.name) }
        }
    ) {

    }

    Surface(modifier = Modifier.padding(3.dp).fillMaxSize()) {
        Column(
            modifier = Modifier.padding(top = 12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(text = "Book id: $bookId")
        }
    }
}