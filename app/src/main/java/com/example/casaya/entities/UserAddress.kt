package com.example.casaya.entities

data class UserAddress(
    private var province: String,
    private var district: String,
    private var street: String,
    private var height: Int,
    private var postalCode: String
)
{
    fun getProvince() : String {
        return province
    }

    fun getDistrict() : String {
        return district
    }

    fun getStreet() : String {
        return street
    }

    fun getHeight() : Int {
        return height
    }

    fun getPostalCode() : String {
        return postalCode
    }
}
