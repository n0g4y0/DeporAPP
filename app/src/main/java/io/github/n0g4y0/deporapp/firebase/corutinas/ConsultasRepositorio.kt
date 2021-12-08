package io.github.n0g4y0.deporapp.firebase.corutinas

import io.github.n0g4y0.deporapp.model.*

interface ConsultasRepositorio {



    suspend fun getUsuarioPorID(idUsuario: String): Result<Usuario>

    suspend fun getCanchaPorId(idCancha: String): Result<Cancha>

    suspend fun getEquipoPorId(idEquipo: String): Result<Equipo>

    suspend fun crearComentario(comentario: Comentario): Result<Void?>

    suspend fun esteUsuarioYaComentoEncuentro(id_encuentro: String, id_usuario: String): Result<Boolean>

    suspend fun crear_P_encuentro(pEncuentro: P_Encuentro): Result<Void?>

    suspend fun crear_P_equipo(pEquipo: P_Equipo): Result<Void?>

}