package io.github.n0g4y0.deporapp.firebase.corutinas

import com.google.firebase.firestore.FirebaseFirestore
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.model.Comentario
import io.github.n0g4y0.deporapp.model.Usuario


private const val COLECCION_USUARIOS = "usuarios"
private const val COLECCION_ENCUENTROS = "encuentros"
private const val COLECCION_COMENTARIOS = "comentarios"
private const val COLECCION_CANCHAS = "canchas"


// valores estaticos, canchas:
private const val CLAVE_ID = "id"
private const val CLAVE_TITULO = "titulo"


class ConsultasRepositorioImpl: ConsultasRepositorio {

    private val authManager = AutentificacionManager()
    private val baseDeDato = FirebaseFirestore.getInstance()

    private val coleccionUsuarios = baseDeDato.collection(COLECCION_USUARIOS)
    private val coleccionEncuentros = baseDeDato.collection(COLECCION_ENCUENTROS)
    private val coleccionComentarios = baseDeDato.collection(COLECCION_COMENTARIOS)
    private val coleccionCanchas = baseDeDato.collection(COLECCION_CANCHAS)


    override suspend fun esteUsuarioYaComentoEncuentro(
        id_encuentro: String,
        id_usuario: String
    ): Result<Boolean> {

        return when (val listaComentariosSnapshot = coleccionComentarios.whereEqualTo("id_usuario",id_usuario).whereEqualTo("id_encuentro",id_encuentro).get().await()){


            is Result.Success -> {
                val existen = listaComentariosSnapshot.data.toObjects(Comentario::class.java)

                Result.Success(existen.isEmpty())
            }

            is Result.Error -> Result.Error(listaComentariosSnapshot.exception)

            is Result.Canceled -> Result.Canceled (listaComentariosSnapshot.exception)
        }


    }



    override suspend fun getUsuarioPorID(idUsuario : String): Result<Usuario> {

        return when (val documentoResultanteSnapshot = coleccionUsuarios.document(idUsuario).get().await()){

            is Result.Success -> {
                    val usuario = documentoResultanteSnapshot.data.toObject(Usuario::class.java)!!
                    Result.Success(usuario)
            }

            is Result.Error -> Result.Error(documentoResultanteSnapshot.exception)

            is Result.Canceled -> Result.Canceled (documentoResultanteSnapshot.exception)
        }


    }

    override suspend fun getCanchaPorId(idCancha: String): Result<Cancha> {

        return when (val documentoResultanteSnapshot = coleccionCanchas.document(idCancha).get().await()){

            is Result.Success -> {
                val cancha = documentoResultanteSnapshot.data.toObject(Cancha::class.java)!!
                Result.Success(cancha)
            }

            is Result.Error -> Result.Error(documentoResultanteSnapshot.exception)

            is Result.Canceled -> Result.Canceled (documentoResultanteSnapshot.exception)
        }


    }

    override suspend fun crearComentario(comentario: Comentario) = coleccionComentarios.document(comentario.id).set(comentario).await()







}