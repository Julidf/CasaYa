package com.example.casaya.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casaya.entities.Property
import com.example.casaya.repositories.PropertyRepository
import kotlinx.coroutines.launch

class UserPropertiesViewModel : ViewModel() {

    private val repositoryProperty: PropertyRepository = PropertyRepository()
    private val viewModelUserLogin: UserLoginViewModel = UserLoginViewModel()
    private val _myListLiveData: MutableLiveData<List<Property>> = MutableLiveData()
    val myListLiveData: LiveData<List<Property>> = _myListLiveData
    var myPropertiesList = mutableListOf<Property>()
    var selectedProperty: Property? = null

    /**
     * Metodo para obtener todas las properties desde  la DB
     */
    fun getMyProperties() {
        viewModelScope.launch {
            myPropertiesList = repositoryProperty.getMyProperties(viewModelUserLogin.getUserUid())
            updateMyListLiveData(myPropertiesList)
            Log.d(
                "ViewModel - My Properties",
                "Mis propiedades $myPropertiesList"
            )
        }
    }

    /**
     * Metodo que actualiza el LiveData '_myListLiveData' con todas las propiedades del User en la DB y permite actualizar a la variable 'myListLiveData' que esta siendo observada en la clase PropertiesListFragment
     */
    private fun updateMyListLiveData(propertiesList: MutableList<Property>) {
        _myListLiveData.postValue(propertiesList)
    }

    fun deleteProperty(propertyToDelete: Property) {
        viewModelScope.launch {
            repositoryProperty.deleteProperty(propertyToDelete)
            getMyProperties()
        }
    }
}