package com.example.casaya.adapters.entities

data class User(
    private var id: String?,
    private var name: String,
    private var lastName: String,
    private var email: String,
    private var password: String,
    private var phone: String,
    private var address: UserAddress
)

{
    fun getId() : String? {
        return id
    }

    fun getName() : String {
        return name
    }

    fun getLastname() : String {
        return lastName
    }

    fun getEmail() : String {
        return email
    }

    fun getPassword() : String {
        return password
    }

    fun getPhone() : String {
        return phone
    }

    fun getAddress() : UserAddress {
        return address
    }

    fun setId(id: String) {
        this.id = id
    }
}
