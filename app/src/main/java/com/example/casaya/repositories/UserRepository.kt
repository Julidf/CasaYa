package com.example.casaya.repositories

import android.util.Log
import android.widget.Toast
import com.example.casaya.entities.Property
import com.example.casaya.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import gun0912.tedimagepicker.util.ToastUtil.context
import kotlinx.coroutines.tasks.await
import java.util.concurrent.Executors

class UserRepository {

    private val COLLECTION: String = "users"
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    /**
     * Guarda un User en la DB
     */
    suspend fun saveUser(newUser: User) {
        try {
            auth.createUserWithEmailAndPassword(newUser.getEmail(), newUser.getPassword())
                .addOnCompleteListener(Executors.newSingleThreadExecutor()) { task ->
                    if (task.isSuccessful) {
                        Log.d("SUCCESS !!!!!!!!!!!!!!!!!!", "createUserWithEmail:success")
                        val user = auth.currentUser
                        val uid = user?.uid

                        if (uid === null) {
                            throw NullPointerException("el uuid es nulo, no se creó correctamente el usuario o algo se rompió :S")
                        }
                        newUser.setId(uid)
                        db.collection(COLLECTION)
                            .document(uid)
                            .set(newUser)

                    } else {
                        Log.w("FAILURE !!!!!!!!!!!!!!!!!!!", "createUserWithEmail:failure", task.exception)

                    }
                }
            Log.d("New User", "Se agrego exitosamente el documento")
        }catch (e: Exception) {
            Log.e("Error Message", "Exception thrown: ${e.message}")
        }
    }

    fun getUser(id: String): Task<DocumentSnapshot> {
        val document = db.collection(COLLECTION).document(id)
        return document.get()
    }

    suspend fun getUsersQuantity() : Int {
        var quantity: Int = 0
        try {
            val documents = db.collection(COLLECTION)
                .get()
                .await()

            val list = documents.toObjects(User::class.java)
            quantity = list.size
        }catch (e: Exception) {
            Log.e("Error Message Firebase getUsersQuantity", "Exception thrown: ${e.message}")
        }

        return quantity
    }

}