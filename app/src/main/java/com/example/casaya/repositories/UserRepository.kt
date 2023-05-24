package com.example.casaya.repositories

import android.util.Log
import com.example.casaya.entities.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val COLLECTION: String = "users"

    //Inicializacion de una instancia de Firestore
    val db = Firebase.firestore

    /**
     * Guarda un User en la DB
     */
    suspend fun saveUser(newUser: User) {
        try {
            val reference = db.collection(COLLECTION).document()
            newUser.setId(reference.id)

            db.collection(COLLECTION)
                .document(reference.id)
                .set(newUser)
                .await()

            Log.d("New User", "Se agrego exitosamente el documento con ID ${reference.id}")
        }catch (e: Exception) {
            Log.e("Error Message", "Exception thrown: ${e.message}")
        }
    }

}