package com.example.casaya.entities

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Date

data class Property(
    private var id: Long,
    private var title: String,
    private var description: String,
    private var city: String,
    private var roomsNumber: Int,
    private var bedRoomsNumber: Int,
    private var bathRoomsNumber: Int,
    private var price: Double,
    private var expense: Double,
    private var isRent: Boolean,
    private var propertyType: PropertyType,
    private var publicationDate: LocalDate
) {
    fun getTitle() : String {
        return this.title
    }

    fun getPrice() : Double {
        return this.price
    }

    fun getPublicationDays() : Long {
        val todayDay = LocalDate.now()
        val publicationDays = ChronoUnit.DAYS.between(publicationDate, todayDay)
        return publicationDays
    }
}
