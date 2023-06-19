package com.example.casaya.repositories

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.casaya.entities.User
import com.example.casaya.interfaces.SaveUserCallback
import com.example.casaya.viewmodels.UserProfileViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.lang.reflect.InvocationTargetException
import java.util.UUID
import java.util.concurrent.Executors

class UserRepository {

    private val COLLECTION: String = "users"
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    //Inicializacion de una instancia de Storage
    private val storage = Firebase.storage

    /**
     * Guarda un User en la DB
     */
    fun saveUser(newUser: User, callback: SaveUserCallback) {
        auth.createUserWithEmailAndPassword(newUser.getEmail()!!, newUser.getPassword()!!)
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
                    Log.w(
                        "FAILURE !!!!!!!!!!!!!!!!!!!",
                        "createUserWithEmail:failure",
                        task.exception
                    )
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        Log.e(
                            "Error Message",
                            "La dirección de correo electrónico ya está en uso: ${exception.message}"
                        )
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

    suspend fun getUsersQuantity(): Int {
        var quantity: Int = 0
        try {
            val documents = db.collection(COLLECTION)
                .get()
                .await()

            val list = documents.toObjects(User::class.java)
            quantity = list.size
        } catch (e: Exception) {
            Log.e("Error Message Firebase getUsersQuantity", "Exception thrown: ${e.message}")
        }
        return quantity
    }

    fun storeUserImage(uri: Uri, viewModel: UserProfileViewModel, context: Context) {
        val storageRef = storage.reference
        val imageRef = storageRef.child(UUID.randomUUID().toString())
        imageRef.putFile(uri).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                viewModel.setUserImageRef(it);
                Log.i("User Image", "Se ha cargado la imagen del user en el Storage $it")
            }
        }.addOnFailureListener { exception ->
            Log.e(
                "Error User Image",
                "Error al cargar la imagen del user en la DB ${exception.message}"
            )
        }
    }

    fun saveUserImage(userId: String, userImageRef: String) {
        try {
            val reference = db.collection(COLLECTION).document(userId)

            reference
                .update("userImageRef", userImageRef)
                .addOnSuccessListener {
                    Log.d(
                        "Update User Image",
                        "Se actualizo exitosamente la imagen del User con ID ${reference.id}"
                    )
                    Log.d(
                        "Update User Image",
                        "Se actualizo exitosamente la imagen del User con ID $userImageRef"
                    )
                }
                .addOnFailureListener { e -> Log.w("Failure Update User Image", "Error updating document", e) }
        } catch (e: InvocationTargetException) {
            Log.e(
                "Error Firebase saveProperty",
                "Exception thrown: ${e.targetException} | ${e.targetException.cause} | ${e.targetException?.cause?.printStackTrace()}"
            )
        }
    }
}