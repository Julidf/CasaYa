package com.example.casaya.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.casaya.entities.User
import com.example.casaya.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class UserProfileViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    var currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private val viewModelUserLogin: UserLoginViewModel = UserLoginViewModel()
    private val repositoryUser: UserRepository = UserRepository()
    var userImageRef: String = ""
    val userPhone: String = ""

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

    fun setUserImage(uri: Uri, context: Context) {
        repositoryUser.storeUserImage(uri, this, context)
    }

    fun setUserImageRef(uri: Uri) {
        Log.i("ViewModel User Image", "Recibi la URL de la imagen: $uri")
        userImageRef = uri.toString()
        Log.i("ViewModel User Image", "Guarde la URL de la imagen: $uri")
        saveUserImageToDb()
    }

    private fun saveUserImageToDb() {
        var userId = viewModelUserLogin.getUserUid()
        repositoryUser.saveUserImage(userId, userImageRef)
        Log.i("ViewModel User Image", "Enviando la userImage a la DB $userImageRef")
    }

    private fun updateUserPhone(userPhone: String) {
        var userId = viewModelUserLogin.getUserUid()
        repositoryUser.updatePhone(userId, userPhone)
        Log.i("ViewModel User Image", "Enviando la userImage a la DB $userImageRef")
    }
    fun setUserPhone(phone: String) {
        Log.i("ViewModel User Phone", "Recibi el telefono: $phone")
        Log.i("ViewModel User Phone", "Guarde el telefono: $phone")
        updateUserPhone(phone)
    }

    fun updateUserDireccion(street: String, height: Int){
        var userId = viewModelUserLogin.getUserUid()
        repositoryUser.updateDireccion(userId, street, height)
    }
    fun setUserDireccion(street: String, height: Int){
        updateUserDireccion(street, height)
    }

    fun updateUserName(name: String){
        var userId = viewModelUserLogin.getUserUid()
        repositoryUser.updateName(userId, name)
    }

    fun setUserName(name: String){
        updateUserName(name)
    }

    fun signOutUser() {
        Log.i("Sign Out ViewModel", "Iniciando metodo singOut de FirebaseAuth")
        auth.signOut()
    }
}