package com.enjoyhac.booklist.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.enjoyhac.booklist.data.DataOrException
import com.enjoyhac.booklist.model.Item
import com.enjoyhac.booklist.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class BookSearchViewModel
@Inject
constructor(private val repository: BookRepository): ViewModel() {
    var listOfBooks: MutableState<DataOrException <List<Item>, Boolean, Exception>>
     = mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        searchBooks("android")
    }

     fun searchBooks(query: String) {
        viewModelScope.launch {
            // Log.d("クエリ実行前", "searchBooks: $query")
            listOfBooks.value = DataOrException(null, true, null) // 再検索時に状態リセット
            if (query.isEmpty()) return@launch
            listOfBooks.value = repository.getBooks(query)
            // Log.d("クエリ実行後", "searchBooks: ${listOfBooks.value.data.toString()}")
        }
    }
}