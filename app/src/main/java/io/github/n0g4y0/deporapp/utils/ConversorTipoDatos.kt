package io.github.n0g4y0.deporapp.utils

import java.util.*

class ConversorTipoDatos {



    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }


    fun fromUUID(uuid: UUID?) : String?{
        return uuid?.toString()
    }

    // funciones para convertir las fechas:


    fun fromDate(date: Date?): Long?{
        return date?.time
    }


    fun toDate(millisSinceEpoch: Long?): Date?{
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

}