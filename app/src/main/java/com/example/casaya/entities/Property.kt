package com.example.casaya.entities

import com.google.firebase.storage.StorageReference
import com.google.firebase.Timestamp
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

data class Property(
    private var title: String = "",
    private var description: String = "",
    private var area: Double = 0.0,
    private var bedRoomsNumber: Int = 0,
    private var bathRoomsNumber: Int = 0,
    private var price: Double = 0.0,
    private var expense: Double = 0.0,
    private var isRent: Boolean = false,
    private var propertyType: String = "",
    private var province: String = "",
    private var street: String = "",
    private var height: Int = 0,
    private var betweenStreets: String = "",
    private var postalCode: String = "",
    private var propertyImageRef: String? = null,
    private var publicationDate: Timestamp = Timestamp.now(),
) {

    private val today = Timestamp.now()

    fun getPropertyImageRef() : String? {
        return this.propertyImageRef
    }

    fun getTitle() : String {
        return this.title
    }

    fun getDescription(): String {
        return this.description
    }

    fun getArea(): Double {
        return this.area
    }

    fun getBedRoomsNumber(): Int {
        return this.bedRoomsNumber
    }

    fun getBathRoomsNumber(): Int {
        return this.bathRoomsNumber
    }

    fun getPrice(): Double {
        return this.price
    }

    fun getExpense(): Double {
        return this.expense
    }

    fun getIsRent(): Boolean {
        return this.isRent
    }

    fun getPropertyType(): String {
        return this.propertyType
    }

    fun getProvince(): String {
        return this.province
    }

    fun getStreet(): String {
        return this.street
    }

    fun getHeight(): Int {
        return this.height
    }

    fun getBetweenStreets(): String {
        return this.betweenStreets
    }

    fun getPostalCode(): String {
        return this.postalCode
    }

    fun getPublicationDate(): Timestamp {
        return this.publicationDate
    }

    /**
     * Obtiene un String para el tipo de operacion de la Propiedad, segun el valor de "isRent"
     */
    fun obtainOperationType(): String {
        return when (isRent) {
            true -> "Alquiler"
            false -> "Venta"
        }
    }

    /**
     * Obtiene un String para la cantidad de dias/horas/minutos qque han transcurrido
     * desde que se ha realizado una publicacion
     */
    fun setStringPublicationDays(): String {
        var message = ""
        val days = calculatePublicationDays()
        message = if (days > 1) {
            "Publicado hace $days dias"
        } else if (days == 1L) {
            "Publicado hace 1 dia"
        } else {
            val hours = calculatePublicationHours()
            if (hours > 1) {
                "Publicado hace $hours horas"
            }else if(hours == 1L) {
                "Publicado hace 1 hora"
            }else {
                val minutes = calculatePublicationMinutes()
                "Publicado hace $minutes minutos"
            }
        }
        return message
    }

    /**
     * Calcula la diferencia en "Dias" entre dos fechas, a partir de fechas de Timestamp
     */
    private fun calculatePublicationDays(): Long {
        // Convierte las fechas de Timestamp a LocalDateTime
        val publicationDayDateTime: LocalDateTime = publicationDate.toDate().toInstant().atZone(
            ZoneId.systemDefault()
        ).toLocalDateTime()
        val todayDateTime: LocalDateTime =
            today.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

        // Convierte las fechas a LocalDate para ignorar la parte de tiempo
        val publicationDayDate: LocalDate = publicationDayDateTime.toLocalDate()
        val todayDate: LocalDate = todayDateTime.toLocalDate()

        // Calcula la diferencia de días entre las dos fechas
        val duration: Duration =
            Duration.between(publicationDayDate.atStartOfDay(), todayDate.atStartOfDay())
        val diasTranscurridos: Long = duration.toDays()

        return diasTranscurridos
    }

    /**
     * Calcula la diferencia en "Horas" entre dos fechas, a partir de fechas de Timestamp
     */
    private fun calculatePublicationHours(): Long {
        // Convierte las fechas de Timestamp a LocalDateTime
        val publicationDayDateTime: LocalDateTime = publicationDate.toDate().toInstant().atZone(
            ZoneId.systemDefault()
        ).toLocalDateTime()
        val todayDateTime: LocalDateTime =
            today.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

        // Calcula la diferencia de horas entre las dos fechas
        val horasTranscurridas: Long =
            Duration.between(publicationDayDateTime, todayDateTime).toHours()

        return horasTranscurridas
    }

    /**
     * Calcula la diferencia en "Minutos" entre dos fechas, a partir de fechas de Timestamp
     */
    private fun calculatePublicationMinutes(): Long {
        // Convierte las fechas de Timestamp a LocalDateTime
        val publicationDayDateTime: LocalDateTime = publicationDate.toDate().toInstant().atZone(
            ZoneId.systemDefault()
        ).toLocalDateTime()
        val todayDateTime: LocalDateTime =
            today.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

        // Calcula la diferencia de minutos entre las dos fechas
        val minutosTranscurridos: Long =
            Duration.between(publicationDayDateTime, todayDateTime).toMinutes()

        return (minutosTranscurridos + 1)
    }

}
