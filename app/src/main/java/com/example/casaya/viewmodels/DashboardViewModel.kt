package com.example.casaya.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casaya.repositories.PropertyRepository
import com.example.casaya.repositories.UserRepository
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val repositoryProperty: PropertyRepository = PropertyRepository()
    private val repositoryUser: UserRepository = UserRepository()

    val usersQuantityLiveData: MutableLiveData<Int> = MutableLiveData()
    val propertiesQuantityLiveData: MutableLiveData<Int> = MutableLiveData()
    val apartmentsQuantityLiveData: MutableLiveData<Int> = MutableLiveData()
    val housesQuantityLiveData: MutableLiveData<Int> = MutableLiveData()
    val phQuantityLiveData: MutableLiveData<Int> = MutableLiveData()

    fun getQuantity() {
        viewModelScope.launch {
            val usersQuantity = repositoryUser.getUsersQuantity()
            Log.d(
                "ViewModel - Dashboard",
                "Cantidad de usuarios $usersQuantity"
            )
            usersQuantityLiveData.postValue(usersQuantity)

            val propertiesQuantity = repositoryProperty.getPropertiesQuantity()
            Log.d(
                "ViewModel - Dashboard",
                "Cantidad de propiedades $propertiesQuantity"
            )
            propertiesQuantityLiveData.postValue(propertiesQuantity)

            val apartmentsQuantity = repositoryProperty.getApartmentQuantity()
            Log.d(
                "ViewModel - Dashboard",
                "Cantidad de Departamentos $apartmentsQuantity"
            )
            apartmentsQuantityLiveData.postValue(apartmentsQuantity)

            val housesQuantity = repositoryProperty.getHousesQuantity()
            Log.d(
                "ViewModel - Dashboard",
                "Cantidad de Casas $housesQuantity"
            )
            housesQuantityLiveData.postValue(housesQuantity)

            val phQuantity = repositoryProperty.getPhQuantity()
            Log.d(
                "ViewModel - Dashboard",
                "Cantidad de PH's $phQuantity"
            )
            phQuantityLiveData.postValue(phQuantity)
        }
    }

}