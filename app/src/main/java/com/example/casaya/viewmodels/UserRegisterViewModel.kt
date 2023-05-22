package com.example.casaya.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.casaya.entities.User
import com.example.casaya.entities.UserAddress

class UserRegisterViewModel : ViewModel() {

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
        postalCode: String
    ) {
        var addressUser = UserAddress(province, district, street, height, postalCode)
        var newUser = User(null, name, lastname, email, password, phoneNumber, addressUser)
        Log.d("User ViewModel", "Se ha dado de alta nuevo usuario ${newUser}")
    }
}