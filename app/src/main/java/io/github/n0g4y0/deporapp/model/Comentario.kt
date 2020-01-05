package io.github.n0g4y0.deporapp.model

import java.sql.Timestamp

data class Comentario(val id: String = "",
                  var puntuacion: Float = 0f,
                  var descripcion: String = "",
                  var fecha: Long = 0L,
                  var id_encuentro: String = "",
                  var id_usuario: String = "",
                  var apodo_usuario: String = ""

)