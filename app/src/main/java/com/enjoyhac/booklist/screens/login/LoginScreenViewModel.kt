package com.enjoyhac.booklist.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enjoyhac.booklist.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {
    // val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    //メールアドレスとパスワードでログインします
    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.d("TAG", "signInWithEmailAndPassword: ログイン成功 ${task.result.toString()}")
                        home()
                    } else {
                        Log.d("TAG", "signInWithEmailAndPassword: ${task.result.toString()}")
                    }
                }

        } catch (ex: Exception) {
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
        }

    }

    //メールアドレスとパスワードでユーザーを作成します
    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if(_loading.value == false) {
            _loading.value == true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val diplayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(diplayName)
                        home()
                    } else {
                        Log.d("TAG", "createUserWithEmailAndPassword: ログイン失敗 ${task.result.toString()}")
                    }
                }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = MUser(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is great",
            profession = "Android Developer",
            id = null,
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
                         .add(user)
    }
}