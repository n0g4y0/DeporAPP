package io.github.n0g4y0.deporapp.firebase.corutinas

import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.model.Comentario
import io.github.n0g4y0.deporapp.model.P_Encuentro
import io.github.n0g4y0.deporapp.model.Usuario

interface ConsultasRepositorio {



    suspend fun getUsuarioPorID(idUsuario: String): Result<Usuario>

    suspend fun getCanchaPorId(idCancha: String): Result<Cancha>

    suspend fun crearComentario(comentario: Comentario): Result<Void?>

    suspend fun esteUsuarioYaComentoEncuentro(id_encuentro: String, id_usuario: String): Result<Boolean>

    suspend fun crear_P_encuentro(pEncuentro: P_Encuentro): Result<Void?>

}