package com.enjoyhac.booklist.screens.details

import android.text.Html
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.enjoyhac.booklist.components.ReaderAppBar
import com.enjoyhac.booklist.components.RoundedButton
import com.enjoyhac.booklist.model.MBook
import com.enjoyhac.booklist.navigation.ReaderNavigation
import com.enjoyhac.booklist.screens.ReaderScreens
import com.enjoyhac.booklist.screens.search.BookRow
import com.enjoyhac.booklist.screens.search.BookSearchViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookDetailsScreen(navController: NavController, bookId: String, viewModel: DetailsViewModel = hiltViewModel()) {

    // ページが開いたときにデータ取得を実行
    LaunchedEffect(key1 = bookId) {
        viewModel.getViewBookInfo(bookId)
    }

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

        val bookData = detailsViewModel.bookInfo.value.data!!.volumeInfo
        val googleBookId = detailsViewModel.bookInfo.value.data!!.id

        Card(
            modifier = Modifier.padding(34.dp),
            shape = CircleShape,
            elevation = 4.dp
        ) {
            Image(painter = rememberImagePainter(data = bookData.imageLinks.thumbnail.replace("http://", "https://")),
                contentDescription = "Book Image",
                modifier = Modifier
                    .width(90.dp)
                    .height(90.dp)
                    .padding(1.dp))
        }
        Text(
            text = bookData.title,
            style = MaterialTheme.typography.h6,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "Authors: ${bookData.authors}",
            overflow = TextOverflow.Clip,
            style = MaterialTheme.typography.caption
        )
        Text(
            text = "Page Count: ${bookData.pageCount}",
            overflow = TextOverflow.Clip,
            style = MaterialTheme.typography.caption
        )
        Text(
            text = "Categories: ${bookData.categories}",
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = "Published: ${bookData.publishedDate}",
            style = MaterialTheme.typography.subtitle1
        )

        Spacer(modifier = Modifier.height(5.dp))

        val cleanDescription = HtmlCompat.fromHtml(bookData!!.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        val localDims = LocalContext.current.resources.displayMetrics
        Surface(
            modifier = Modifier
                .height(localDims.heightPixels.dp.times(0.09f))
                .padding(4.dp),
            shape = RectangleShape,
            border = BorderStroke(1.dp, Color.DarkGray)
            ) {
            LazyColumn(modifier = Modifier.padding(3.dp)) {
                item { Text(text = cleanDescription) }
            }
        }

        Row(
            modifier = Modifier.padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RoundedButton(label = "Save") {
//                val db = FirebaseFirestore.getInstance()
                val book = MBook(
                    title = bookData?.title,
                    authors = bookData?.authors.toString(),
                    description = bookData?.description,
                    categories = bookData?.categories.toString(),
                    notes = "",
                    photoUrl = bookData?.imageLinks.thumbnail,
                    publishedDate = bookData?.publishedDate,
                    pageCount = bookData?.pageCount.toString(),
                    rating = 0.0,
                    googleBookId = googleBookId,
                    userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

                )
                saveToFirebase(book, navController = navController)
            }
            Spacer(modifier = Modifier.width(25.dp))
            RoundedButton(label = "Cancel") {
                navController.popBackStack()
            }
        }
    }
}

fun saveToFirebase(book: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotEmpty()){
        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                            .update(hashMapOf("id" to docId) as Map<String, Any> )
                            .addOnCompleteListener { task->
                                if(task.isSuccessful) {
                                    navController.popBackStack()
                                }
                            }.addOnFailureListener {
                                Log.d("ReaderBiikDetailsScreen(Error)", "saveToFirebase: Error updting doc", it)
                            }
            }
    }
}