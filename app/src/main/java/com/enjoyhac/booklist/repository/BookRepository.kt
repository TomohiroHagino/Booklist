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
   // private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
   // private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()

    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
        val result = DataOrException<List<Item>, Boolean, Exception>()

        try {
            result.loading = true
            result.data = api.getAllBooks(searchQuery).items
            result.loading = false
        } catch (e: Exception) {
            result.e = e
            Log.d("BookRepository(Exception)", "getBooks: $e")
        }
        return result
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        val result = DataOrException<Item, Boolean, Exception>()

        try {
            result.loading = true
            result.data = api.getBookInfo(bookId)
            result.loading = false
        } catch (e: Exception) {
            result.e = e
            Log.d("BookRepository(Exception)", "getBookInfo: $e")
        }
        return result
    }
}