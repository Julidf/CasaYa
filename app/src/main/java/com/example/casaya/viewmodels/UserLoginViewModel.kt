package com.example.casaya.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserLoginViewModel : ViewModel() {

    //private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    fun getUserUid() : String {
        Log.d("Login2 User", "Usuario logeado: ID ${currentUser?.uid} / ${currentUser?.email}")
        Log.d("Login2 User", "Usuario logeado: $currentUser")
        return currentUser?.uid ?: ""
    }

    fun userIsLoggedIn() : Boolean {
        return currentUser != null
    }

}