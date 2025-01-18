package com.enjoyhac.booklist.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.enjoyhac.booklist.components.InputField
import com.enjoyhac.booklist.components.ReaderAppBar
import com.enjoyhac.booklist.model.Item
import com.enjoyhac.booklist.model.MBook
import com.enjoyhac.booklist.screens.ReaderScreens

@Composable
fun SearchScreen(navController: NavController, viewModel: BookSearchViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Books",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController,
        ) { navController.navigate(ReaderScreens.ReaderHomeScreen.name) }
    }) {
        Surface() {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    viewModel
                )
                { query ->
                    viewModel.searchBooks(query)
                }
                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController, viewModel)
            }
        }
    }
}


@Composable
fun BookList(navController: NavController, viewModel: BookSearchViewModel) {

    if (viewModel.listOfBooks.value.loading == true) {
        Log.d("ReaderBookSearchScreen(loading...)", "BookList: ${viewModel.listOfBooks.value.data}")
        CircularProgressIndicator()
    } else {
        Log.d("ReaderBookSearchScreen(loadning is done)", "BookList: ${viewModel.listOfBooks.value.data}")

        val listOfBooks = viewModel.listOfBooks.value.data!!

        LazyColumn(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(items = listOfBooks) { book ->
                BookRow(book, navController)
            }
        }
    }
}


@Composable
fun BookRow(book: Item, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable { }
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top
        ) {
            val imageUrl = if (book.volumeInfo.imageLinks == null) {
                "https://books.google.com/books/content?id=qKFDDAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            } else {
                book.volumeInfo.imageLinks.smallThumbnail.replace("http://", "https://")
            }
            Image(
                painter = rememberImagePainter(data = imageUrl),
                "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            )
            Column() {
                Text(text = book.volumeInfo.title, overflow = TextOverflow.Ellipsis)
                Text(
                    text = "Author: ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: BookSearchViewModel,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    Column() {
        val searchQueryState = rememberSaveable {
            mutableStateOf("")
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }

}