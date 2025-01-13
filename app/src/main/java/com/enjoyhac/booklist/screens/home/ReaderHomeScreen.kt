package com.enjoyhac.booklist.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.enjoyhac.booklist.components.FABContent
import com.enjoyhac.booklist.components.ReaderAppBar
import com.enjoyhac.booklist.components.TitleSection
import com.enjoyhac.booklist.model.MBook
import com.enjoyhac.booklist.screens.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@Preview
@Composable
fun Home(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(
        topBar = {
                 ReaderAppBar(title = "Booklist", navController = navController )
        },
        floatingActionButton = {
            FABContent{

            }
        },
    ) {
        //content
        Surface(modifier = Modifier.fillMaxSize()) {
            //home content
            HomeContect(navController)
        }
    }
}



@Composable
fun HomeContect(navController: NavController) {

    val currentUserName = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0) else "N/A"
    Column(Modifier.padding(2.dp),
        verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "あなたは現在、読書中です。。。")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
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
                Divider()
            }
        }
    }
}


@Preview
@Composable
fun ListCard(
    book: MBook = MBook("xxx", "Running","Me and you","Hello world!"),
    onPressDetails: (String) -> Unit ={}
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(
        shape = RoundedCornerShape(29.dp),
        backgroundColor = Color.White,
        elevation = 6.dp,
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable { onPressDetails.invoke(book.title.toString()) }
    ) {
        Column(
            modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment =  Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = rememberImagePainter(data = ""),
                    contentDescription = "book image",
                    modifier = Modifier
                                    .height(140.dp)
                                    .width(100.dp)
                                    .padding(4.dp)
                )
                Spacer(
                    modifier = Modifier.width(50.dp)
                )
            }
        }
    }
}

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {

}

