package com.enjoyhac.booklist.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.enjoyhac.booklist.components.*
import com.enjoyhac.booklist.model.MBook
import com.enjoyhac.booklist.screens.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()) {
    Scaffold(
        topBar = { ReaderAppBar(title = "Booklist", navController = navController ) },
        floatingActionButton = {
            FABContent{ navController.navigate(ReaderScreens.SearchScreen.name) }
        },
    ) {
        //content
        Surface(modifier = Modifier.fillMaxSize()) {
            //home content
            HomeContent(navController, viewModel)
        }
    }
}

@Composable
fun HomeContent(navController: NavController = NavController(LocalContext.current), viewModel: HomeScreenViewModel) {

    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if(!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value?.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }
    }

//    val listOfBooks = listOf(
//        MBook(id = "data1", title = "Hello Again 1", authors = "All of us", notes = null),
//        MBook(id = "data2", title = "Hello Again 2", authors = "All of us", notes = null),
//        MBook(id = "data3", title = "Hello Again 3", authors = "All of us", notes = null),
//        MBook(id = "data4", title = "Hello Again 4", authors = "All of us", notes = null),
//        MBook(id = "data5", title = "Hello Again 5", authors = "All of us", notes = null),
//    )
    
    val currentUserName = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0) else "N/A"
    Column(
        verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            Column(Modifier.padding(start = 10.dp)) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { navController.navigate(ReaderScreens.ReaderStatsScreen.name) },
                    tint = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = currentUserName!!,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
//                Divider()
            }
            TitleSection(label = "あなたは現在、読書中です。。。")
//            Spacer()
        }
        ReadingRightNowArea(
            books = listOf(),
            navController = navController
        )
        TitleSection(label = "Reading List")

        BookListArea(listOfBooks = listOfBooks, navController = navController)

    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    HorizontalScrollableComponent(listOfBooks) {
        Log.d("TAG","BookListArea $it")
        // Todo: Cardをクリックすると詳細に移動する
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    onCardPressed: (String) -> Unit)
{
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)) {

            for (book in listOfBooks) {
                ListCard(book) {
                    onCardPressed(it)
                }
            }
        }
}

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {
    ListCard()
}


