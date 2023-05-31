package com.example.casaya.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casaya.entities.Property
import com.example.casaya.repositories.PropertyRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch

class PublishPropertyViewModel : ViewModel() {

    private val repositoryProperty: PropertyRepository = PropertyRepository()
    private var properties = MutableLiveData<MutableList<Property>>()

    private var mutableListProperties: MutableList<Property> = mutableListOf()
    private var property = MutableLiveData<Property>()

    private lateinit var property2: Property

    /*
    private var title = MutableLiveData<String>()
    private var description = MutableLiveData<String>()
    private var province = MutableLiveData<String>()
    private var street = MutableLiveData<String>()
    private var height = MutableLiveData<Int>()
    private var betweenStreets = MutableLiveData<String>()
    private var postalCode = MutableLiveData<String>()
    private var area = MutableLiveData<Double>()
    private var bedRoomsNumber = MutableLiveData<Int>()
    private var bathRoomsNumber = MutableLiveData<Int>()
    private var price = MutableLiveData<Double>()
    private var expense = MutableLiveData<Double>()
    private var isRent = MutableLiveData<Boolean>()
    private var propertyType = MutableLiveData<String>()
    */



    fun publishProperty(title: String, description: String, area: Double, bedRoomsNumber: Int, bathRoomsNumber: Int, price: Double, expense: Double, isRent: Boolean, propertyType: String, province: String, street: String, height: Int, betweenStreets: String, postalCode: String) {
        var newProperty = Property(
            null, title, description, area, bedRoomsNumber, bathRoomsNumber, price,
            expense, isRent, propertyType, province, street, height, betweenStreets,
            postalCode, null, Timestamp.now()
        )
        properties.value?.add(newProperty)
        //properties.value = properties.value
        Log.d("Propiedad Agregada", "Datos: ${properties.value?.last()?.getTitle()}")

        this.property.value = newProperty
        //property.value = property.value
        Log.d("Propiedad Agregada", "Datos: ${property.value?.getTitle()}")

        property2 = newProperty

        viewModelScope.launch {
            repositoryProperty.saveProperty(newProperty)
        }
    }

    fun getProperties() : LiveData<MutableList<Property>> {
        return this.properties
    }

    fun getMutableList() : MutableList<Property> {
        properties.value?.let { mutableListProperties.addAll(it) }
        return mutableListProperties
    }

    fun getProperty() : LiveData<Property> {
        return this.property
    }

    fun getProperty2() : Property {
        return this.property2
    }



}