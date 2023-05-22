package com.example.casaya.entities

data class UserAddress(
    private var province: String,
    private var district: String,
    private var street: String,
    private var height: Int,
    private var postalCode: String
)
{}
