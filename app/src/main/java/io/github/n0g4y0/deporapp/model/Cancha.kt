package io.github.n0g4y0.deporapp.model



data class Cancha(val id: String = "",
                  var titulo: String = "",
                  var foto_url: String = "",
                  var ubicacion_lat: Double = 0.0,
                  var ubicacion_long: Double = 0.0,
                  var deporte_futbol: Boolean = false,
                  var deporte_futsal: Boolean = false,
                  var deporte_basquet: Boolean = false,
                  var deporte_voley: Boolean = false,
                  var fecha_timestamp: Long = 0L,
                  var autor: String = ""
){
    val nombreArchivoFoto
        get() = "IMG_$id.jpg"
}