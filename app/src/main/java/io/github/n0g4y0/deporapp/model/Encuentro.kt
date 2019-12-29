package io.github.n0g4y0.deporapp.model

data class Encuentro(
    val id: String = "",
    var nombre: String = "",
    var id_cancha: String = "",
    var fecha: Long = 0L,
    var hora: Long = 0L,
    var cupos: Int = 0,
    var nota: String = "",
    var deporte: String = "",
    var esPrivado: Boolean = false,
    var id_creador: String = "",
    var fecha_creacion: Long = 0L
)