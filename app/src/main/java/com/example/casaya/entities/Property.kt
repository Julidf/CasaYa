package com.example.casaya.entities

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Date

data class Property(
    private var title: String,
    private var description: String,
    private var area: Double,
    private var bedRoomsNumber: Int,
    private var bathRoomsNumber: Int,
    private var price: Double,
    private var expense: Double,
    private var isRent: Boolean,
    private var propertyType: String,
    private var province: String,
    private var street: String,
    private var height: Int,
    private var betweenStreets: String,
    private var postalCode: String

    //constructor(): this("", "", 0.0, 0, 0, 0.0, 0.0, false, "", "", "", 0, "", "")
) {

    constructor(): this("", "", 0.0, 0, 0, 0.0, 0.0, false, "", "", "", 0, "", "")

    private var publicationDate: LocalDate

    init {
        publicationDate = LocalDate.now()
    }

    fun getTitle() : String {
        return this.title
    }

    fun getDescription() : String {
        return this.description
    }

    fun getArea() : Double {
        return this.area
    }

    fun getBedRoomsNumber() : Int {
        return this.bedRoomsNumber
    }

    fun getBathRoomsNumber() : Int {
        return this.bathRoomsNumber
    }

    fun getPrice() : Double {
        return this.price
    }

    fun getExpense() : Double {
        return this.expense
    }

    fun getIsRent() : Boolean {
        return this.isRent
    }

    fun getPropertyType() : String {
        return this.propertyType
    }

    fun getProvince() : String {
        return this.province
    }

    fun getStreet() : String {
        return this.street
    }

    fun getHeight() : Int {
        return this.height
    }

    fun getBetweenStreets() : String {
        return this.betweenStreets
    }

    fun getPostalCode() : String {
        return this.postalCode
    }

    fun getPublicationDays() : Long {
        val todayDay = LocalDate.now()
        val publicationDays = ChronoUnit.DAYS.between(publicationDate, todayDay)
        return publicationDays
    }
}
