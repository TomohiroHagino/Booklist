package com.enjoyhac.booklist.repository

import android.provider.ContactsContract.Data
import com.enjoyhac.booklist.data.DataOrException
import com.enjoyhac.booklist.model.MBook
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(
    private val queryBook: Query
    ) {
    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
        } catch(exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }
        return dataOrException
    }

}