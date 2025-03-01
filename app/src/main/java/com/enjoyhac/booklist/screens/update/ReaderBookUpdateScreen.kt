package com.enjoyhac.booklist.screens.update

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.enjoyhac.booklist.components.ReaderAppBar
import com.enjoyhac.booklist.data.DataOrException
import com.enjoyhac.booklist.model.MBook
import com.enjoyhac.booklist.screens.home.HomeScreenViewModel

@Composable
fun BookUpdateScreen(
    navController: NavHostController,
    bookItemId: String,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Update Book",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) {
        val bookInfo = produceState<DataOrException<List<MBook>, Boolean, Exception>>(
            initialValue = DataOrException(
                data = emptyList(),
                true,
                Exception("")
            )
        ) {
            value = viewModel.data.value
        }.value

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = CenterHorizontally
            ) {
                Log.d("INFO", "BookUpdateScreen: ${viewModel.data.value.data.toString()}")

                if (bookInfo.loading == true) {
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else {
                    Text(text = viewModel.data.value.data?.get(0)?.title.toString())
//                    Surface(
//                        modifier = Modifier
//                            .padding(2.dp)
//                            .fillMaxWidth(),
//                        shape = CircleShape,
//                        elevation = 4.dp
//                    ) {
//                        ShowBookUpdate(
//                            bookInfo = viewModel.data.value,
//                            bookItemId = bookItemId
//                        )
//                    }
//
//                    ShowSimpleForm(
//                        book = viewModel.data.value.data?.first { mBook ->
//                            mBook.googleBookId == bookItemId
//                        }!!,
//                        navController = navController
//                    )
                }
            }
        }
    }
}
