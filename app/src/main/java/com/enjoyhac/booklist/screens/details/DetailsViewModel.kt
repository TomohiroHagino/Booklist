package com.enjoyhac.booklist.screens.details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjoyhac.booklist.data.DataOrException
import com.enjoyhac.booklist.model.Item
import com.enjoyhac.booklist.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {

    var bookInfo: MutableState<DataOrException<Item, Boolean, Exception>>
            = mutableStateOf(DataOrException(null, true, null))

    init {
        getViewBookInfo("")
    }

    fun getViewBookInfo(bookId: String) {
        viewModelScope.launch {
            Log.d("クエリ実行前", "getViewBookInfo: $bookId")
            bookInfo.value = DataOrException(null, true, null) // 再検索時に状態リセット
            if (bookId.isEmpty()) return@launch
            bookInfo.value = repository.getBookInfo(bookId)
            Log.d("クエリ実行後", "getViewBookInfo: ${bookInfo.value.data.toString()}")
        }
    }
}