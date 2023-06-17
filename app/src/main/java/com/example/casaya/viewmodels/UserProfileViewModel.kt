package com.example.casaya.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casaya.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class UserProfileViewModel : ViewModel() {


    suspend fun obtenerUsuarioPorId(userId: String): User? = withContext(Dispatchers.IO) {
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")
        val documentSnapshot = usersCollection.document(userId).get().await()

        if (documentSnapshot.exists()) {
            val usuario = documentSnapshot.toObject(User::class.java)
            usuario
        } else {
            null
        }
    }

}