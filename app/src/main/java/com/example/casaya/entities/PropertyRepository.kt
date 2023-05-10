package com.example.casaya.entities

import android.util.Log
import java.time.LocalDate

class PropertyRepository(){

    private var properties: MutableList<Property>

    init {
        this.properties = mutableListOf()
        initializeList()
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

    fun getProperties() : MutableList<Property> {
        return this.properties
    }

    fun saveProperty(newProperty: Property) {
        Log.d("Saving Property", "Guardando la nueva Property")
        Log.d("Tamaño ArrayList ANTES", "El tamaño es ${properties.size}")
        properties.add(newProperty)
        Log.d("Nueva Property", "El titulo de la propiedad es: ${properties.get(properties.lastIndex).getTitle()}")
        Log.d("Tamaño ArrayList DESPUES", "El tamaño es ${properties.size}")
    }
}
