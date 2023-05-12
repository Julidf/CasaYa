package com.example.casaya.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.casaya.entities.Property
import com.example.casaya.entities.PropertyRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PropertiesListViewModel : ViewModel() {
    private val repositoryProperty: PropertyRepository = PropertyRepository()

    fun publishProperty(
        title: String,
        description: String,
        area: Double,
        bedRoomsNumber: Int,
        bathRoomsNumber: Int,
        price: Double,
        expense: Double,
        isRent: Boolean,
        propertyType: String,
        province: String,
        street: String,
        height: Int,
        betweenStreets: String,
        postalCode: String
    ) {
        val newProperty = Property(
            title,
            description,
            area,
            bedRoomsNumber,
            bathRoomsNumber,
            price,
            expense,
            isRent,
            propertyType,
            province,
            street,
            height,
            betweenStreets,
            postalCode
        )

        repositoryProperty.saveProperty(newProperty)
    }

    fun getProperties(): MutableList<Property> {
        return repositoryProperty.getAllProperties()
    }


}