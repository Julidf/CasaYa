package com.example.casaya.interfaces

interface SaveUserCallback {
    fun onSuccess()
    fun onEmailCollision()
    fun onError(errorMessage: String)
}