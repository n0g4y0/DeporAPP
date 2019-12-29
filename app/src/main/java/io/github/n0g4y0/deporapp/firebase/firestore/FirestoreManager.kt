package io.github.n0g4y0.deporapp.firebase.firestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager
import io.github.n0g4y0.deporapp.model.*
import kotlinx.coroutines.tasks.await


private const val COLECCION_CANCHAS = "canchas"
private const val COLECCION_ANUNCIOS = "anuncios"
private const val COLECCION_EQUIPOS = "equipos"
private const val COLECCION_USUARIOS = "usuarios"
private const val COLECCION_ENCUENTROS = "encuentros"




// valores estaticos, canchas:
private const val CLAVE_ID = "id"
private const val CLAVE_TITULO = "titulo"
private const val CLAVE_FOTO_URL = "foto_url"
private const val CLAVE_UBICACION_LAT = "ubicacion_lat"
private const val CLAVE_UBICACION_LNG = "ubicacion_long"
private const val CLAVE_FUTBOL = "futbol"
private const val CLAVE_FUTSAL = "futsal"
private const val CLAVE_BASQUET = "basquet"
private const val CLAVE_VOLEY = "voley"
private const val CLAVE_FECHA = "fecha_timestamp"
private const val CLAVE_CREADOR = "autor"


// valores estaticos, anuncios:

private const val CLAVE_ID_ANUNCIO = "id"
private const val CLAVE_TITULO_ANUNCIO = "titulo"
private const val CLAVE_TITULO_DESCRIPCION = "descripcion"

// valores staticos, equipos:

private const val CLAVE_EQUIPO_NOMRE = "nombre"
private const val CLAVE_DESCRIPCION_EQUIPO = "descripcion"
private const val CLAVE_ID_ADMIN = "idAdmin"


// valores estaticos, usuarios:

private const val CLAVE_ID_USUARIO = "id"
private const val CLAVE_NOMBRE_USUARIO = "nombre"
private const val CLAVE_CORREO_USUARIO = "correo"
private const val CLAVE_FOTO_URL_USUARIO = "foto_url"
private const val CLAVE_NUMERO_USUARIO = "telefono"

// valores estaticos, encuentros deportivos:

private const val CLAVE_ID_ENCUENTRO = "id"
private const val CLAVE_NOMBRE_ENCUENTRO = "nombre"
private const val CLAVE_ID_CANCHA_ENCUENTRO = "id_cancha"
private const val CLAVE_FECHA_ENCUENTRO = "fecha"
private const val CLAVE_HORA_ENCUENTRO = "hora"
private const val CLAVE_CUPOS_ENCUENTRO = "cupos"
private const val CLAVE_NOTA_ENCUENTRO = "nota"
private const val CLAVE_DEPORTE_ENCUENTRO = "deporte"
private const val CLAVE_TIPO_ENCUENTRO = "esPrivado"
private const val CLAVE_ID_CREADOR_ENCUENTRO = "id_creador"
private const val CLAVE_FECHA_CREACION_ENCUENTRO = "fecha_creacion"




private lateinit var registrosCanchas: ListenerRegistration
private lateinit var registrosAnuncios: ListenerRegistration
private lateinit var registrosEquipos: ListenerRegistration

private lateinit var registrosUsuarios: ListenerRegistration
private lateinit var registrosEncuentros: ListenerRegistration

class FirestoreManager {

    private val authManager = AutentificacionManager()
    private val baseDeDato = FirebaseFirestore.getInstance()

    private val valoresCanchas = MutableLiveData<List<Cancha>>()

    private val valoresAnuncios = MutableLiveData<List<Anuncio>>()

    private val valoresEquipos = MutableLiveData<List<Equipo>>()

    private val valoresUsuarios = MutableLiveData<List<User>>()

    private val valoresEncuentros = MutableLiveData<List<Encuentro>>()



    fun agregarCancha(titulo: String,
                      urlFoto: String,
                      ubicacionLat: Double,
                      ubicacionLng: Double,
                      siFutbol: Boolean,
                      siFutsal: Boolean,
                      siBasquet: Boolean,
                      siVoley: Boolean,
                      enAcciondeExito: () -> Unit, enAcciondeFracaso: () -> Unit ){

        val referenciaDocumento = baseDeDato.collection(COLECCION_CANCHAS).document()

        val cancha = HashMap<String,Any>()

        cancha[CLAVE_ID] = referenciaDocumento.id
        cancha[CLAVE_TITULO] = titulo
        cancha[CLAVE_FOTO_URL] = urlFoto
        cancha[CLAVE_UBICACION_LAT] = ubicacionLat
        cancha[CLAVE_UBICACION_LNG] = ubicacionLng
        cancha[CLAVE_FUTBOL] = siFutbol
        cancha[CLAVE_FUTSAL] = siFutsal
        cancha[CLAVE_BASQUET] = siBasquet
        cancha[CLAVE_VOLEY] = siVoley
        cancha[CLAVE_FECHA] = getTiempoActual()
        cancha[CLAVE_CREADOR] = authManager.getUsuarioActual()


        referenciaDocumento
            .set(cancha)
            .addOnSuccessListener { enAcciondeExito() }
            .addOnFailureListener { enAcciondeFracaso() }



    }

    fun enCambiosDeValorCanchas(): LiveData<List<Cancha>>{

        escucharPorCambiosEnValoresCanchas()
        return valoresCanchas

    }

    fun cambiosDeValorAnuncios(): LiveData<List<Anuncio>>{
        escucharPorCambiosEnValoresAnuncios()
        return valoresAnuncios
    }

    /*
    * funciones para manipular datos referidos a Equipos
    *
    * */

    fun agregarEquipo(nombre: String,
                      descripcion: String,
                      siFutbol: Boolean,
                      siFutsal: Boolean,
                      siBasquet: Boolean,
                      siVoley: Boolean,
                      enAcciondeExito: () -> Unit, enAcciondeFracaso: () -> Unit ){

        val referenciaDocumento = baseDeDato.collection(COLECCION_EQUIPOS).document()

        val equipo = HashMap<String,Any>()

        equipo[CLAVE_ID] = referenciaDocumento.id
        equipo[CLAVE_EQUIPO_NOMRE] = nombre
        equipo[CLAVE_DESCRIPCION_EQUIPO] = descripcion
        equipo[CLAVE_ID_ADMIN] = authManager.getIdUsuarioActual()
        equipo[CLAVE_FUTBOL] = siFutbol
        equipo[CLAVE_FUTSAL] = siFutsal
        equipo[CLAVE_BASQUET] = siBasquet
        equipo[CLAVE_VOLEY] = siVoley
        equipo[CLAVE_FECHA] = getTiempoActual()


        referenciaDocumento
            .set(equipo)
            .addOnSuccessListener { enAcciondeExito() }
            .addOnFailureListener { enAcciondeFracaso() }
    }

    fun cambiosDeValorEquipos(): LiveData<List<Equipo>>{
        escucharPorCambiosEnValoresEquipos()
        return valoresEquipos
    }



    private fun escucharPorCambiosEnValoresEquipos() {

        registrosEquipos = baseDeDato.collection(COLECCION_EQUIPOS)

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->

                if (error != null || valor == null) {
                    return@EventListener
                }

                if (valor.isEmpty) {

                    valoresEquipos.postValue(emptyList())

                } else {

                    val equipos = ArrayList<Equipo>()

                    for (doc in valor) {
                        val equipo = doc.toObject(Equipo::class.java)
                        equipos.add(equipo)
                    }

                    valoresEquipos.postValue(equipos)
                }
            })

    }






    private fun escucharPorCambiosEnValoresCanchas() {

        registrosCanchas = baseDeDato.collection(COLECCION_CANCHAS)

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->
                // 4
                if (error != null || valor == null) {
                    return@EventListener
                }
                // 5
                if (valor.isEmpty) {

                    // 6
                    valoresCanchas.postValue(emptyList())
                } else {
                    // 7
                    val canchas = ArrayList<Cancha>()
                    // 8
                    for (doc in valor) {
                        // 9
                        val cancha = doc.toObject(Cancha::class.java)
                        canchas.add(cancha)
                    }
                    // 10
                    valoresCanchas.postValue(canchas)
                }
            })

    }

    private fun escucharPorCambiosEnValoresAnuncios() {
        registrosAnuncios = baseDeDato.collection(COLECCION_ANUNCIOS)

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->
                // 4
                if (error != null || valor == null) {
                    return@EventListener
                }
                // 5
                if (valor.isEmpty) {

                    // 6
                    valoresAnuncios.postValue(emptyList())
                } else {
                    // 7
                    val anuncios = ArrayList<Anuncio>()
                    // 8
                    for (doc in valor) {
                        // 9
                        val anuncio = doc.toObject(Anuncio::class.java)
                        anuncios.add(anuncio)
                    }
                    // 10
                    valoresAnuncios.postValue(anuncios)
                }
            })
    }


    /*
    * agregar al usuario a la base de datos:
    *
    * */

    fun agregarUsuario(id: String,
                       nombre: String,
                      correo: String,
                      foto_url: String,
                      enAcciondeExito: () -> Unit, enAcciondeFracaso: () -> Unit ){

        val referenciaDocumento = baseDeDato.collection(COLECCION_USUARIOS).document()

        val usuarios = HashMap<String,Any>()

        usuarios[CLAVE_ID] = id
        usuarios[CLAVE_NOMBRE_USUARIO] = nombre
        usuarios[CLAVE_CORREO_USUARIO] = correo
        usuarios[CLAVE_FOTO_URL_USUARIO] = foto_url
        usuarios[CLAVE_FECHA] = getTiempoActual()


        referenciaDocumento
            .set(usuarios)
            .addOnSuccessListener { enAcciondeExito() }
            .addOnFailureListener { enAcciondeFracaso() }
    }

    suspend fun estaIdUsuario(idUsuario: String): Boolean{

        try {
            val usuarios = baseDeDato.collection(COLECCION_USUARIOS).whereEqualTo(CLAVE_ID_USUARIO,idUsuario).get().await()
            return usuarios.isEmpty
            }catch (e: FirebaseFirestoreException){
                return false
            }

    }

    /*
    * Agregando los metodos para manipular los encuentros deportivos:
    *
    * */

    fun agregarEncuentro(
                        nombre: String,
                        idCancha:String,
                         fecha: Long,
                         hora: Long,
                         cupos: Int,
                         nota: String,
                         deporte: String,
                         esPrivado: Boolean,
                       enAcciondeExito: () -> Unit, enAcciondeFracaso: () -> Unit ){

        val referenciaDocumento = baseDeDato.collection(COLECCION_ENCUENTROS).document()

        val encuentro = HashMap<String,Any>()

        encuentro[CLAVE_ID_ENCUENTRO] = referenciaDocumento.id
        encuentro[CLAVE_NOMBRE_ENCUENTRO] = nombre
        encuentro[CLAVE_ID_CANCHA_ENCUENTRO] = idCancha
        encuentro[CLAVE_FECHA_ENCUENTRO] = fecha
        encuentro[CLAVE_HORA_ENCUENTRO] = hora
        encuentro[CLAVE_CUPOS_ENCUENTRO] = cupos
        encuentro[CLAVE_NOTA_ENCUENTRO] = nota
        encuentro[CLAVE_DEPORTE_ENCUENTRO] = deporte
        encuentro[CLAVE_TIPO_ENCUENTRO] = esPrivado
        encuentro[CLAVE_ID_CREADOR_ENCUENTRO] = authManager.getIdUsuarioActual()
        encuentro[CLAVE_FECHA_CREACION_ENCUENTRO] = getTiempoActual()


        referenciaDocumento
            .set(encuentro)
            .addOnSuccessListener { enAcciondeExito() }
            .addOnFailureListener { enAcciondeFracaso() }
    }

    fun cambiosDeValorEncuentros(): LiveData<List<Encuentro>>{
        escucharPorCambiosEnValoresEncuentros()
        return valoresEncuentros
    }

    private fun escucharPorCambiosEnValoresEncuentros() {

        registrosEncuentros = baseDeDato.collection(COLECCION_ENCUENTROS)

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->

                if (error != null || valor == null) {
                    return@EventListener
                }

                if (valor.isEmpty) {

                    valoresEncuentros.postValue(emptyList())

                } else {

                    val encuentros = ArrayList<Encuentro>()

                    for (doc in valor) {
                        val encuentro = doc.toObject(Encuentro::class.java)
                        encuentros.add(encuentro)
                    }

                    valoresEncuentros.postValue(encuentros)
                }
            })

    }

    /*
    * funciones de consulta de uso comun:
    * */

    suspend fun buscarUsuarioPorID(idUsuario: String): User{

            val snapshot = baseDeDato.collection(COLECCION_USUARIOS).whereEqualTo(CLAVE_ID_USUARIO,idUsuario).get().await()
            if (!snapshot.isEmpty) {
                val usuarios = ArrayList<User>()

                for (doc in snapshot){
                    val usuario = doc.toObject(User::class.java)
                    usuarios.add(usuario)
                }

                return usuarios.first()
            }else{
                return User()
            }
    }

    suspend fun buscarCanchaPorID(idCancha: String): Cancha{

        val snapshot = baseDeDato.collection(COLECCION_CANCHAS).whereEqualTo(CLAVE_ID,idCancha).get().await()
        if (!snapshot.isEmpty) {
            val canchas = ArrayList<Cancha>()

            for (doc in snapshot){
                val cancha = doc.toObject(Cancha::class.java)
                canchas.add(cancha)
            }

            return canchas.first()
        }else{
            return Cancha()
        }
    }


    private fun getTiempoActual() = System.currentTimeMillis()



}