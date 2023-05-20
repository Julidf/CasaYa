package com.example.casaya.entities

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.reflect.TypeVariable
import java.time.LocalDate

class PropertyRepository(){

    private val COLLECTION: String = "properties"

    //Inicializacion de una instancia de Firestore
    val db = Firebase.firestore
    //Inicializacion de una instancia de Storage
    val storage = Firebase.storage

    private var properties: MutableList<Property>

    init {
        this.properties = mutableListOf()
        initializeList()
    }

    fun saveProperty(newProperty: Property) {
        db.collection(COLLECTION)
            .add(newProperty)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Se agrego exitosamente el documento con ID ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error agregando el documento", e)
            }
    }

    fun getAllProperties() : MutableList<Property> {
        var propertiesList: MutableList<Property> = arrayListOf()
        db.collection(COLLECTION)
            .get()
            .addOnSuccessListener { snapshot ->
                if(snapshot != null) {
                    for (propertyFirebase in snapshot) {
                        Log.d("Property", "${propertyFirebase.id} => ${propertyFirebase.data}")
                        val property: Property = propertyFirebase.toObject<Property>()
                        propertiesList.add(property)
                    }
                }
            }
        return propertiesList
    }

    //TO DO
    fun editProperty (property: Property) : Property {
        return property;
    }

    //TO DO
    fun deleteProperty (property: Property) : Boolean {
        return true;
    }

    private fun initializeList() {

        val prop1 = Property( "Apartment_1", "Hermoso depto", 45.0, 3, 2, 25000.0, 120000.0, true, "Departamento", "Alquiler", "BUenos Aires", 2051, "Junin y Ayacucho", "Junin y Ayacucho")
        val prop2 = Property( "Apartment_2", "Hermoso depto", 50.0, 2, 2, 65000.0, 130000.0, true, "Departamento", "Alquiler", "BUenos Aires", 2051, "Junin y Ayacucho", "Junin y Ayacucho")
        val prop3 = Property( "Apartment_3", "Hermoso depto", 55.0, 5, 2, 75000.0, 140000.0, false, "Casa", "Alquiler", "BUenos Aires", 2051, "Junin y Ayacucho", "Junin y Ayacucho")
        val prop4 = Property( "Apartment_4", "Hermoso depto", 60.0, 6, 2, 80000.0, 150000.0, false, "Departamento", "Alquiler", "BUenos Aires", 2051, "Junin y Ayacucho", "Junin y Ayacucho")
        val prop5 = Property( "Apartment_5", "Hermoso depto", 65.0, 1, 2, 95000.0, 160000.0, true, "PH", "Alquiler", "BUenos Aires", 2051, "Junin y Ayacucho", "Junin y Ayacucho")

        properties.add(prop1)
        properties.add(prop2)
        properties.add(prop3)
        properties.add(prop4)
        properties.add(prop5)

    }

}
