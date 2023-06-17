package com.example.casaya.repositories

import android.util.Log
import android.widget.Toast
import com.example.casaya.entities.Property
import com.example.casaya.entities.User
import com.example.casaya.interfaces.SaveUserCallback
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
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
    fun saveUser(newUser: User, callback: SaveUserCallback) {
        auth.createUserWithEmailAndPassword(newUser.getEmail(), newUser.getPassword())
            .addOnCompleteListener(Executors.newSingleThreadExecutor()) { task ->
                if (task.isSuccessful) {
                    Log.d("SUCCESS !!!!!!!!!!!!!!!!!!", "createUserWithEmail:success")
                    val user = auth.currentUser
                    val uid = user?.uid

                    if (uid == null) {
                        callback.onError("el uuid es nulo, no se creó correctamente el usuario o algo se rompió :S")
                    } else {
                        newUser.setId(uid)
                        db.collection(COLLECTION)
                            .document(uid)
                            .set(newUser)
                        Log.d("New User", "Se agregó exitosamente el documento")
                        callback.onSuccess()
                    }
                } else {
                    Log.w("FAILURE !!!!!!!!!!!!!!!!!!!", "createUserWithEmail:failure", task.exception)
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        Log.e("Error Message", "La dirección de correo electrónico ya está en uso: ${exception.message}")
                        callback.onEmailCollision()
                    } else {
                        Log.e("Error Message", "Excepción lanzada: ${exception?.message}")
                        callback.onError(exception?.message ?: "Error desconocido")
                    }
                }
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