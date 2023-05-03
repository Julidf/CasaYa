package com.example.casaya.entities

import java.time.LocalDate

class PropertyRepository(){

    private var properties: MutableList<Property>

    init {
        this.properties = mutableListOf()
        initializeList()
    }


    private fun initializeList() {

        val prop1 = Property(1, "Apartment_1", "Hermoso depto", "Buenos Aires", 3, 2, 2, 120000.0, 15000.0, true, PropertyType.DEPARTAMENTO, LocalDate.of(2023, 4, 1))
        val prop2 = Property(2, "Apartment_2", "Hermoso depto", "Buenos Aires", 3, 2, 2, 130000.0, 16000.0, true, PropertyType.DEPARTAMENTO, LocalDate.now())
        val prop3 = Property(3, "Apartment_3", "Hermoso depto", "Buenos Aires", 3, 2, 2, 140000.0, 17000.0, true, PropertyType.DEPARTAMENTO, LocalDate.of(2023, 1, 1))
        val prop4 = Property(4, "Apartment_4", "Hermoso depto", "Buenos Aires", 3, 2, 2, 150000.0, 18000.0, true, PropertyType.DEPARTAMENTO, LocalDate.now())
        val prop5 = Property(5, "Apartment_5", "Hermoso depto", "Buenos Aires", 3, 2, 2, 160000.0, 19000.0, true, PropertyType.DEPARTAMENTO, LocalDate.of(2023, 2, 10))

        properties.add(prop1)
        properties.add(prop2)
        properties.add(prop3)
        properties.add(prop4)
        properties.add(prop5)

    }

    fun getProperties() : MutableList<Property> {
        return this.properties
    }
}
