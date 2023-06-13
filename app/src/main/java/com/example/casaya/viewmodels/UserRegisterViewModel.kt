package com.example.casaya.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casaya.entities.User
import com.example.casaya.entities.UserAddress
import com.example.casaya.repositories.UserRepository
import kotlinx.coroutines.launch

class UserRegisterViewModel : ViewModel() {

    private val repositoryUser = UserRepository()
    var welcomeMessage: String = ""

    fun createNewUser(
        name: String,
        lastname: String,
        email: String,
        password: String,
        phoneNumber: String,
        province: String,
        district: String,
        street: String,
        height: Int,
        postalCode: String,
    ) {
        var addressUser = UserAddress(province, district, street, height, postalCode)
        var newUser = User(null, name, lastname, email, password, phoneNumber, addressUser, null)

        saveNewUser(newUser)
        Log.d("User ViewModel", "Se ha dado de alta nuevo usuario ${newUser}")
    }

    /**
     * Metodo para guardar un nuevo User en la DB a traves de Repository
     */
    private fun saveNewUser(newUser: User) {
        viewModelScope.launch {
            repositoryUser.saveUser(newUser)
        }
    }
}