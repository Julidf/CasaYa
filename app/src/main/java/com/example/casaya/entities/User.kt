package com.example.casaya.entities

data class User(
    private var id: String?,
    private var name: String,
    private var lastName: String,
    private var email: String,
    private var password: String,
    private var phone: String,
    private var address: UserAddress
)
{}
