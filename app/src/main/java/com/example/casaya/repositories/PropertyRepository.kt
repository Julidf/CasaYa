package com.example.casaya.repositories

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.casaya.entities.Property
import com.example.casaya.viewmodels.PropertiesListViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.util.*
import java.lang.reflect.InvocationTargetException

class PropertyRepository(){

    private val COLLECTION: String = "properties"

    //Inicializacion de una instancia de Firestore
    private val db = Firebase.firestore
    //Inicializacion de una instancia de Storage
    private val storage = Firebase.storage


    fun savePropertyImage(uri: Uri, viewModel: PropertiesListViewModel, context: Context) {
        val storageRef = storage.reference
        val imageRef = storageRef.child(UUID.randomUUID().toString())
        imageRef.putFile(uri).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                viewModel.setPropertyImageRef(it);
            }
        }.addOnFailureListener { exception ->
            showErrorDialog("SE ROMPIO", context)
        }
    }

    private fun showErrorDialog(message: String, context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    /**
     * Guarda una Property en la DB
     */
    suspend fun saveProperty(newProperty: Property) {
        try {
            val reference = db.collection(COLLECTION).document()

             newProperty.setId(reference.id)
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
                .orderBy("publicationDate", Query.Direction.DESCENDING)
                .get()
                .await()

            propertiesList = documents.toObjects(Property::class.java)
        }catch (e: Exception) {
            Log.e("Error Message Firebase getAllProperties", "Exception thrown: ${e.message}")
        }

        return propertiesList
    }

    /**
     * Metodo que realiza una busqueda de propiedades de acuerdo al criterio de consulta, y
     * devuelve una MutableList con el resultado de la busqueda
     */
    suspend fun searchPropertiesByProvince(query: String) : MutableList<Property> {
        var propertiesListSearched = mutableListOf<Property>()
        Log.i("Filtered Properties", "Query: $query")
        try {
            val documents = db.collection(COLLECTION)

            val allProperties = documents
                .orderBy("publicationDate", Query.Direction.DESCENDING)
                .get()
                .await()

            val auxPropertiesList = allProperties.toObjects(Property::class.java)

            propertiesListSearched = auxPropertiesList.filter {
                it.getProvince().contains(query, true)
            }.toMutableList()

            Log.i("Filtered Properties", "Cantidad: ${propertiesListSearched.size}, Propiedades: $propertiesListSearched")
        }catch (e: Exception) {
            Log.e("Filtered Properties", "Exception thrown: ${e.message}")
        }

        return propertiesListSearched

    }

    suspend fun getMyProperties(userId: String): MutableList<Property> {
        var myPropertiesList = mutableListOf<Property>()
        Log.i("Find my Properties", "Query: $userId")
        try {
            val documents = db.collection(COLLECTION)

            val allProperties = documents
                .orderBy("publicationDate", Query.Direction.DESCENDING)
                .get()
                .await()

            val auxPropertiesList = allProperties.toObjects(Property::class.java)

            myPropertiesList = auxPropertiesList.filter {
                it.getUserId().contains(userId, false)
            }.toMutableList()

            Log.i("Repository - My Properties", "Cantidad: ${myPropertiesList.size}, Propiedades: $myPropertiesList")
        }catch (e: Exception) {
            Log.e("Filtered Properties", "Exception thrown: ${e.message}")
        }
        return myPropertiesList
    }

    suspend fun updateProperty(propertyData: Property) {
        try {
            val documentId = propertyData.getId()
            if (documentId != null) {
                val reference = db.collection(COLLECTION).document(documentId)

                reference
                    .set(propertyData)
                    .await()

               Log.d("Update Property", "Se actualizo exitosamente el documento con ID ${reference.id}")
            }

        }catch (e: InvocationTargetException) {
            Log.e("Error Firebase saveProperty", "Exception thrown: ${e.targetException} | ${e.targetException.cause} | ${e.targetException?.cause?.printStackTrace()}")
        }
    }

    suspend fun deleteProperty(property: Property) {
        try {
            val documentId = property.getId()
            if (documentId != null) {
                val reference = db.collection(COLLECTION).document(documentId)

                reference
                    .delete()
                    .await()

                Log.d("Delete Property", "Se elimino exitosamente el documento con ID ${reference.id}")
            }

        }catch (e: InvocationTargetException) {
            Log.e("Error Firebase saveProperty", "Exception thrown: ${e.targetException} | ${e.targetException.cause} | ${e.targetException?.cause?.printStackTrace()}")
        }
    }

}
