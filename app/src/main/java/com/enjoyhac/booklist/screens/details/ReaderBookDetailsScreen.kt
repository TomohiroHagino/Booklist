package com.enjoyhac.booklist.screens.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.enjoyhac.booklist.components.ReaderAppBar
import com.enjoyhac.booklist.navigation.ReaderNavigation
import com.enjoyhac.booklist.screens.ReaderScreens
import com.enjoyhac.booklist.screens.search.BookRow
import com.enjoyhac.booklist.screens.search.BookSearchViewModel

@Composable
fun BookDetailsScreen(navController: NavController, bookId: String, viewModel: DetailsViewModel = hiltViewModel()) {

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
        Surface(modifier = Modifier
            .padding(3.dp)
            .fillMaxSize()) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                val data = viewModel.getViewBookInfo(bookId = bookId)
                ShowBookDetails(navController, viewModel)
            }
        }
    }
}

@Composable
fun ShowBookDetails(navController: NavController, detailsViewModel: DetailsViewModel) {
    if (detailsViewModel.bookInfo.value.loading == true) {
        Log.d("BookDetailsScreen(loading...)", "BookInfo: ${detailsViewModel.bookInfo.value.data}")
        LinearProgressIndicator()
    } else {
        Log.d("BookDetailsScreen(loadning is done)", "BookInfo: ${detailsViewModel.bookInfo.value.data}")

        val book = detailsViewModel.bookInfo.value.data!!

//        LazyColumn(
//            modifier = Modifier.padding(16.dp).fillMaxSize(),
//            contentPadding = PaddingValues(16.dp)
//        ) {
//            item {
//                Text(text = "Book id: ${bookInfo.id}")
//                Text(text = "Book data: ${bookInfo.volumeInfo}")
//            }
//        }

        Card(
            modifier = Modifier.padding(34.dp),
            shape = CircleShape,
            elevation = 4.dp
        ) {
            Image(painter = rememberImagePainter(data = book.volumeInfo.imageLinks.thumbnail),
                contentDescription = "Book Image",
                modifier = Modifier.width(90.dp).height(90.dp).padding(1.dp))
            Text(text = book.volumeInfo.title, overflow = TextOverflow.Ellipsis)
            Text(
                text = "Authors: ${book.volumeInfo.authors}",
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.caption
            )
            Text(
                text = "Page Count: ${book.volumeInfo.pageCount}",
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.caption
            )
            Text(
                text = "Categories: ${book.volumeInfo.categories}",
                overflow = TextOverflow.Clip,
//                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "Published: ${book.volumeInfo.publishedDate}",
                overflow = TextOverflow.Clip,
//                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}