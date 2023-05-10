package com.example.casaya.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.casaya.entities.Property
import com.example.casaya.entities.PropertyRepository

class PropertiesListViewModel : ViewModel() {
    private val repositoryProperty: PropertyRepository = PropertyRepository()

    private var properties = MutableLiveData<MutableList<Property>>()

    private var mutableListProperties: MutableList<Property> = mutableListOf()
    private var property = MutableLiveData<Property>()

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
        properties.value?.add(newProperty)
        properties = properties

        repositoryProperty.saveProperty(newProperty)
    }

    fun getProperties(): LiveData<MutableList<Property>> {
        return this.properties
    }

    fun getMutableList(): MutableList<Property> {
        properties.value?.let { mutableListProperties.addAll(it) }
        return mutableListProperties
    }

    fun getProperty(): LiveData<Property> {
        return this.property
    }

}