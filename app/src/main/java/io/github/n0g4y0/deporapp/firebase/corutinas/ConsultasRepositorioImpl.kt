package io.github.n0g4y0.deporapp.firebase.corutinas

import com.google.firebase.firestore.FirebaseFirestore
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.model.Comentario
import io.github.n0g4y0.deporapp.model.Usuario


private const val COLECCION_USUARIOS = "usuarios"
private const val COLECCION_ENCUENTROS = "encuentros"
private const val COLECCION_COMENTARIOS = "comentarios"


// valores estaticos, canchas:
private const val CLAVE_ID = "id"
private const val CLAVE_TITULO = "titulo"


class ConsultasRepositorioImpl: ConsultasRepositorio {


    private val authManager = AutentificacionManager()
    private val baseDeDato = FirebaseFirestore.getInstance()

    private val coleccionUsuarios = baseDeDato.collection(COLECCION_USUARIOS)
    private val coleccionEncuentros = baseDeDato.collection(COLECCION_ENCUENTROS)
    private val coleccionComentarios = baseDeDato.collection(COLECCION_COMENTARIOS)



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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun crearComentario(comentario: Comentario) = coleccionComentarios.document().set(comentario).await()



}