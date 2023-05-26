package com.example.casaya.repositories

import android.util.Log
import com.example.casaya.entities.Property
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.reflect.InvocationTargetException

class PropertyRepository(){

    private val COLLECTION: String = "properties"

    //Inicializacion de una instancia de Firestore
    val db = Firebase.firestore

    /**
     * Guarda una Property en la DB
     */
    suspend fun saveProperty(newProperty: Property) {
        try {
            val reference = db.collection(COLLECTION).document()

            db.collection(COLLECTION)
                .document(reference.id)
                .set(newProperty)
                .await()

            Log.d("New Property", "Se agrego exitosamente el documento con ID ${reference.id}")
        }catch (e: InvocationTargetException) {
            Log.e("Error Firebase saveProperty", "Exception thrown: ${e.targetException} | ${e.targetException.cause} | ${e.targetException?.cause?.printStackTrace()}")
        }
    }

    /**
     * Devuelve todas las propiedades almacenadas en la DB
     */
    suspend fun getAllProperties() : MutableList<Property> {
        var propertiesList = mutableListOf<Property>()
        try {
            val documents = db.collection(COLLECTION)
                .orderBy("title", Query.Direction.ASCENDING)
                .get()
                .await()

            propertiesList = documents.toObjects(Property::class.java)
        }catch (e: Exception) {
            Log.e("Error Message Firebase getAllProperties", "Exception thrown: ${e.message}")
        }

        return propertiesList
    }

    suspend fun searchPropertiesByTitle(query: String) : MutableList<Property> {
        var propertiesListSearched = mutableListOf<Property>()
        Log.i("Filtered Properties", "Query: $query")
        try {
            val documents = db.collection(COLLECTION)

            val found = documents
                .whereGreaterThanOrEqualTo("title", query)
                .whereLessThanOrEqualTo("title", query + "\uf8ff")
                .orderBy("title")
                .get()
                .await()

            propertiesListSearched = found.toObjects(Property::class.java)
            Log.i("Filtered Properties", "Propiedades: $propertiesListSearched")
        }catch (e: Exception) {
            Log.e("Filtered Properties", "Exception thrown: ${e.message}")
        }

        return propertiesListSearched

    }

}
