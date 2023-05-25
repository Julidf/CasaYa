package com.example.casaya.repositories

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.example.casaya.entities.Property
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File

class PropertyRepository(){

    private val COLLECTION: String = "properties"

    //Inicializacion de una instancia de Firestore
    val db = Firebase.firestore
    //Inicializacion de una instancia de Storage
    val storage = Firebase.storage

    suspend fun savePropertyImage(uri: String) {
        val storageRef = storage.reference

        var file = Uri.fromFile(File(uri))
        val imageRef = storageRef.child("propertyImages/${file.lastPathSegment}")
        val uploadTask = imageRef.putFile(file)

        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
        }
    }

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
        }catch (e: Exception) {
            Log.e("Error Message", "Exception thrown: ${e.message}")
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
            Log.e("Error Message", "Exception thrown: ${e.message}")
        }

        return propertiesList
    }
}
