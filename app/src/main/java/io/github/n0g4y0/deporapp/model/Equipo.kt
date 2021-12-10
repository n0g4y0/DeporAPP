package io.github.n0g4y0.deporapp.model

data class Equipo(val id: String = "",
                  var nombre: String = "",
                  var descripcion: String = "",
                  var idAdmin: String = "",
                  var esFutbol: Boolean = false,
                  var esFutsal: Boolean = false,
                  var esBasquet: Boolean = false,
                  var esVoley: Boolean = false,
                  val fecha_timestamp: Long = 0L,
                  var cantidad_integrantes: Int = 1)