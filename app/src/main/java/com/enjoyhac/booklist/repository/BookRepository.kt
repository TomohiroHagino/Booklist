package com.enjoyhac.booklist.repository

import android.provider.ContactsContract.Data
import android.util.Log
import com.enjoyhac.booklist.data.DataOrException
import com.enjoyhac.booklist.model.Item
import com.enjoyhac.booklist.network.BooksApi
import retrofit2.http.Query
import javax.inject.Inject

// repository
// データソース（例: API、ローカルデータベース、キャッシュなど）とのやり取りを管理し、そのロジックを分離する
class BookRepository @Inject constructor(private val api: BooksApi) {
    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
    private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()

    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllBooks(searchQuery).items
            if (dataOrException.data!!.isNotEmpty()) dataOrException.loading = false

        } catch(e: Exception) {
            dataOrException.e = e
            Log.d("BookRepository(Exception)", "getBooks: $e")
        }

        // <List<Item>, Boolean, Exception>の値が返る
        return dataOrException
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        try {
            bookInfoDataOrException.loading = true
            bookInfoDataOrException.data = api.getBookInfo(bookId)
            if (bookInfoDataOrException.data.toString().isNotEmpty()) bookInfoDataOrException.loading = false

        } catch(e: Exception) {
            bookInfoDataOrException.e = e
            Log.d("BookRepository(Exception)", "getBookInfo: $e")
        }

        // <Item, Boolean, Exception>の値が返る
        return bookInfoDataOrException
    }
}