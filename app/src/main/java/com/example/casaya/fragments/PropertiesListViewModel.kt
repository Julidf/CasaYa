package com.example.casaya.fragments

import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casaya.entities.Property
import com.example.casaya.entities.PropertyRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class PropertiesListViewModel : ViewModel() {
    private val repositoryProperty: PropertyRepository = PropertyRepository()

    private val _myListLiveData: MutableLiveData<List<Property>> = MutableLiveData()
    val myListLiveData: LiveData<List<Property>> = _myListLiveData
    lateinit var search: SearchView

    var propertiesList = mutableListOf<Property>()

    fun publishProperty(
        title: String, description: String, area: Double, bedRoomsNumber: Int,
        bathRoomsNumber: Int, price: Double, expense: Double, isRent: Boolean, propertyType: String,
        province: String, street: String, height: Int, betweenStreets: String, postalCode: String
    ) {
        val newProperty = Property(
            title, description, area, bedRoomsNumber, bathRoomsNumber, price,
            expense, isRent, propertyType, province, street, height, betweenStreets, postalCode
        )
        saveNewProperty(newProperty)
    }

    fun filterPropertiesByName(query: String) {
        val filteredProperties = _myListLiveData.value?.filter { property ->
            property.getTitle().contains(query, ignoreCase = true)
        }
        filteredProperties?.let {
            _myListLiveData.value = it
        }
    }

    /**
     * Metodo para guardar Property en la DB a traves de Repository
     */
    private fun saveNewProperty(newProperty: Property) {
        viewModelScope.launch {
            repositoryProperty.saveProperty(newProperty)
        }
    }

    /**
     * Metodo para obtener todas las properties desde  la DB
     */
    fun getProperties() {
        viewModelScope.launch {
            propertiesList = repositoryProperty.getAllProperties()
        }
    }

    /**
     * Metodo que actualiza el LiveData '_myListLiveData' con todas las propiedades que hay en la DB y permite actualizar a la variable 'myListLiveData' que esta siendo observada en la clase PropertiesListFragment
     */
    fun fetchMyList() {
        viewModelScope.launch {
            try {
                val myList = repositoryProperty.getAllProperties()
                _myListLiveData.postValue(myList)
            } catch (e: Exception) {
                Log.e("Error Message", "Exception thrown: ${e.message}")
            }
        }
    }

}