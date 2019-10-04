package io.github.n0g4y0.deporapp.models

import java.util.*

data class Cancha(val id: UUID = UUID.randomUUID(),
                  var titulo: String = "",
                    //val ubicacion: Ubicacion,
                  var deporte_futbol8: Boolean = false,
                  var deporte_futsal: Boolean = false,
                  var date: Date = Date(),
                  var deporte_basquet: Boolean = false,
                  var deporte_voley: Boolean = false){
    val nombreArchivoFoto
        get() = "IMG_$id.jpg"
}