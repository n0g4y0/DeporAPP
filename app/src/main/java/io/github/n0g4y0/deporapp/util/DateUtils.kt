package io.github.n0g4y0.deporapp.util

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_PATTERN = "yyyy-MM-dd"

class DateUtils {

    fun mapToNormalisedDateText(timestamp: Long): String {
        val date = Date(timestamp)
        val formatter = SimpleDateFormat(DATE_PATTERN, Locale.US)

        return formatter.format(date)
    }

    fun convertirFechaAString(fecha: Date): String{

        //yyyy-MM-dd

        var formato: String = "dd-MM-yyyy"

        val formatter = SimpleDateFormat(formato, Locale.US)

        return formatter.format(fecha)
    }
    fun convertirHoraAString(hora: Date): String{

        var formato: String = "HH:mm"

        val formatter = SimpleDateFormat(formato, Locale.US)

        return formatter.format(hora)
    }

    fun convertirTimeStampAHora(dato: Long): String{
        var formato: String = "HH:mm"

        val formatter = SimpleDateFormat(formato, Locale.US)

        return formatter.format(dato)
    }

    fun convertirTimestampNombreMes(dato: Long): String{


        var formato: String = "MMMM"

        // esta linea de abajo traduce a idioma español, el dia del mes (en este caso)
        val formatter = SimpleDateFormat(formato, Locale.forLanguageTag("es-ES"))

        return formatter.format(dato)
    }


    fun convertirTimestampDia(dato: Long):String{
        var formato = "dd"
        val formatter = SimpleDateFormat(formato, Locale.US)
        return  formatter.format(dato)
    }

    fun convertirTimestampsAStringCompleto(dato: Long): String{
        var formato = "dd-MM-yyyy HH:mm"
        val formatter = SimpleDateFormat(formato, Locale.US)
        return  formatter.format(dato)
    }

}