package io.github.n0g4y0.deporapp.firebase.corutinas

import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.model.Comentario
import io.github.n0g4y0.deporapp.model.Usuario

interface ConsultasRepositorio {



    suspend fun getUsuarioPorID(idUsuario: String): Result<Usuario>

    suspend fun getCanchaPorId(idCancha: String): Result<Cancha>

    suspend fun crearComentario(comentario: Comentario): Result<Void?>

}