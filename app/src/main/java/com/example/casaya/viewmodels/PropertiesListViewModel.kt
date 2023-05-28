package com.example.casaya.viewmodels

import android.content.Context
import android.net.Uri
import java.net.URI
import android.util.Log
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.casaya.entities.Property
import com.example.casaya.repositories.PropertyRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

class PropertiesListViewModel : ViewModel() {
    private val repositoryProperty: PropertyRepository = PropertyRepository()

    private val _myListLiveData: MutableLiveData<List<Property>> = MutableLiveData()
    val myListLiveData: LiveData<List<Property>> = _myListLiveData
    lateinit var propertyImageRef: String
    var propertiesList = mutableListOf<Property>()

    fun publishProperty(
        title: String, description: String, area: Double, bedRoomsNumber: Int,
        bathRoomsNumber: Int, price: Double, expense: Double, isRent: Boolean, propertyType: String,
        province: String, street: String, height: Int, betweenStreets: String, postalCode: String
    ) {
        val newProperty = Property(
            title, description, area, bedRoomsNumber, bathRoomsNumber, price,
            expense, isRent, propertyType, province, street, height, betweenStreets, postalCode, propertyImageRef
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

    fun setPropertyImage(uri: Uri, context: Context) {
        viewModelScope.launch {
            propertyImageRef = repositoryProperty.savePropertyImage(uri, context)
            Log.d("CCCCCCCCCCCCCCCCCCCCC", propertyImageRef)
        }
    }

    fun getPropertyImage(): Uri? {
        var imageUri: Uri? = null
        viewModelScope.launch {
            imageUri = repositoryProperty.getPropertyImage(propertyImageRef)
            Log.d("DDDDDDDDDDDDDDDDDDD", imageUri.toString())
            Log.d("EEEEEEEEEEEEEEEEEEE", propertyImageRef)
        }
        return imageUri
    }

}