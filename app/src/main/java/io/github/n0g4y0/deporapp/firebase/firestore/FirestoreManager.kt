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
private const val COLECCION_COMENTARIOS = "comentarios"
private const val COLECCION_P_EQUIPOS = "p_equipos"
private const val COLECCION_P_ENCUENTROS = "p_encuentros"





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
private const val CLAVE_VERIFICADO = "verificado"


// valores estaticos, anuncios:

private const val CLAVE_ID_ANUNCIO = "id"
private const val CLAVE_TITULO_ANUNCIO = "titulo"
private const val CLAVE_TITULO_DESCRIPCION = "descripcion"

// valores staticos, equipos:

private const val CLAVE_EQUIPO_NOMRE = "nombre"
private const val CLAVE_DESCRIPCION_EQUIPO = "descripcion"
private const val CLAVE_ID_ADMIN = "idAdmin"

// valores staticos,  participantes equipos:

private const val CLAVE_P_EQUIPO_ID = "id"
private const val CLAVE_P_EQUIPO_ID_EQUIPO = "id_equipo"
private const val CLAVE_P_EQUIPO_ID_USUARIO_ACTUAL = "id_usuario_actual"


// valores estaticos, usuarios:

private const val CLAVE_ID_USUARIO = "id"
private const val CLAVE_NOMBRE_USUARIO = "nombre"
private const val CLAVE_CORREO_USUARIO = "correo"
private const val CLAVE_FOTO_URL_USUARIO = "foto_url"
private const val CLAVE_NUMERO_USUARIO = "telefono"
private const val CLAVE_APODO_USUARIO = "apodo"

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
private const val CLAVE_LAT_ENCUENTRO = "fk_cancha_lat"
private const val CLAVE_LNG_ENCUENTRO = "fk_cancha_lng"
private const val CLAVE_NICK_ENCUENTRO = "fk_usuario_nick"
private const val CLAVE_FOTO_URL_ENCUENTRO = "fk_usuario_foto_url"
private const val CLAVE_ID_EQUIPO = "id_equipo"


// valores estaticos, comentarios sobre encuentros:

private const val CLAVE_ID_COMENTARIO = "id"
private const val CLAVE_PUNTUACION_COMENTARIO = "puntuacion"
private const val CLAVE_DESCRIPCION_COMENTARIO = "descripcion"
private const val CLAVE_FECHA_COMENTARIO = "fecha"
private const val CLAVE_ID_ENCUENTRO_COMENTARIO = "id_encuentro"
private const val CLAVE_ID_USUARIO_COMENTARIO = "id_usuario"


// valores estaticos, participantes de encuentros deportivos:

private const val CLAVE_ID_P_ENCUENTRO = "id_encuentro"




private lateinit var registrosCanchas: ListenerRegistration
private lateinit var registrosAnuncios: ListenerRegistration
private lateinit var registrosEquipos: ListenerRegistration

private lateinit var registrosUsuarios: ListenerRegistration
private lateinit var registrosEncuentros: ListenerRegistration
private lateinit var registrosComentarios: ListenerRegistration

private lateinit var registrosEncuentrosPendientes : ListenerRegistration
private lateinit var registrosEncuentrosConcluidos : ListenerRegistration

private lateinit var registrosEncuentrosByIdCancha : ListenerRegistration
private lateinit var registros_P_Encuentros : ListenerRegistration

class FirestoreManager {

    private val authManager = AutentificacionManager()
    private val baseDeDato = FirebaseFirestore.getInstance()

    private val valoresCanchas = MutableLiveData<List<Cancha>>()

    private val valoresAnuncios = MutableLiveData<List<Anuncio>>()

    private val valoresEquipos = MutableLiveData<List<Equipo>>()

    private val valores_P_Equipos = MutableLiveData<List<P_Equipo>>()

    private val valoresUsuarios = MutableLiveData<List<Usuario>>()

    private val valoresEncuentros = MutableLiveData<List<Encuentro>>()

    private val valoresEncuentrosPendientes = MutableLiveData<List<Encuentro>>()
    private val valoresEncuentrosConcluidos = MutableLiveData<List<Encuentro>>()

    private val valoresComentarios = MutableLiveData<List<Comentario>>()


    private val valoresEncuentrosByIdCancha = MutableLiveData<List<Encuentro>>()

    private val valores_P_Encuentros = MutableLiveData<List<P_Encuentro>>()




    fun agregarCancha(titulo: String,
                      urlFoto: String,
                      ubicacionLat: Double,
                      ubicacionLng: Double,
                      siFutbol: Boolean,
                      siFutsal: Boolean,
                      siBasquet: Boolean,
                      siVoley: Boolean,
                      verificado: Boolean,
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
        cancha[CLAVE_VERIFICADO] = verificado
        cancha[CLAVE_FECHA] = getTiempoActual()
        cancha[CLAVE_CREADOR] = authManager.getNombreUsuarioActual()


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

    fun cambiosDeValor_P_Equipos(idUsuarioActual: String): LiveData<List<P_Equipo>>{
        escucharPorCambiosEnValores_P_Equipos(idUsuarioActual)
        return valores_P_Equipos
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

    private fun escucharPorCambiosEnValores_P_Equipos(idUsuarioActual: String) {

        registrosEquipos = baseDeDato.collection(COLECCION_P_EQUIPOS)
            .whereEqualTo(CLAVE_P_EQUIPO_ID_USUARIO_ACTUAL,idUsuarioActual)

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->

                if (error != null || valor == null) {
                    return@EventListener
                }

                if (valor.isEmpty) {

                    valores_P_Equipos.postValue(emptyList())

                } else {

                    val p_equipos = ArrayList<P_Equipo>()

                    for (doc in valor) {
                        val p_equipo = doc.toObject(P_Equipo::class.java)
                        p_equipos.add(p_equipo)
                    }

                    valores_P_Equipos.postValue(p_equipos)
                }
            })

    }






    private fun escucharPorCambiosEnValoresCanchas() {

        registrosCanchas = baseDeDato.collection(COLECCION_CANCHAS)
            .whereEqualTo(CLAVE_VERIFICADO,true)

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

        val referenciaDocumento = baseDeDato.collection(COLECCION_USUARIOS).document(id)

        val usuarios = HashMap<String,Any>()

        usuarios[CLAVE_ID] = id
        usuarios[CLAVE_NOMBRE_USUARIO] = nombre
        usuarios[CLAVE_CORREO_USUARIO] = correo
        usuarios[CLAVE_FOTO_URL_USUARIO] = foto_url
        usuarios[CLAVE_FECHA] = getTiempoActual()
        usuarios[CLAVE_APODO_USUARIO] = obtenerApodoDesdeCorreo(correo)


        referenciaDocumento
            .set(usuarios)
            .addOnSuccessListener { enAcciondeExito() }
            .addOnFailureListener { enAcciondeFracaso() }
    }

    private fun obtenerApodoDesdeCorreo(correo: String): String {

        return correo.substringBefore('@')


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
                        fk_cancha_lat: Double,
                        fk_cancha_lng: Double,
                        fk_usuario_nick: String,
                        fk_usuario_foto_url: String,
                        id_equipo: String,
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
        encuentro[CLAVE_LAT_ENCUENTRO] = fk_cancha_lat
        encuentro[CLAVE_LNG_ENCUENTRO] = fk_cancha_lng
        encuentro[CLAVE_NICK_ENCUENTRO] = fk_usuario_nick
        encuentro[CLAVE_FOTO_URL_ENCUENTRO] = fk_usuario_foto_url
        encuentro[CLAVE_ID_EQUIPO] = id_equipo


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
            .whereEqualTo(CLAVE_TIPO_ENCUENTRO,false)
            .whereGreaterThan(CLAVE_FECHA_ENCUENTRO, getTiempoActual())
            .orderBy(CLAVE_FECHA_ENCUENTRO, Query.Direction.DESCENDING)

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


    fun listenerCambiosDeValorEncuentrosPendientes(): LiveData<List<Encuentro>>{
        listenerDeValoresEncuentrosPendientes()
        return valoresEncuentrosPendientes
    }

    private fun listenerDeValoresEncuentrosPendientes() {

        registrosEncuentrosPendientes = baseDeDato.collection(COLECCION_ENCUENTROS)
            .whereEqualTo(CLAVE_ID_CREADOR_ENCUENTRO,authManager.getIdUsuarioActual())
            .whereGreaterThanOrEqualTo(CLAVE_FECHA_ENCUENTRO,getTiempoActual())

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->

                if (error != null || valor == null) {
                    return@EventListener
                }



                if (valor.isEmpty) {

                    valoresEncuentrosPendientes.postValue(emptyList())

                } else {

                    val encuentros = ArrayList<Encuentro>()

                    for (doc in valor) {
                        val encuentro = doc.toObject(Encuentro::class.java)
                        encuentros.add(encuentro)
                    }

                    valoresEncuentrosPendientes.postValue(encuentros)
                }
            })

    }



    fun listenerCambiosDeValorEncuentrosConcluidos(): LiveData<List<Encuentro>>{
        listenerDeValoresEncuentrosConcluidos()
        return valoresEncuentrosConcluidos
    }

    private fun listenerDeValoresEncuentrosConcluidos() {

        registrosEncuentrosConcluidos = baseDeDato.collection(COLECCION_ENCUENTROS)
            .whereEqualTo(CLAVE_ID_CREADOR_ENCUENTRO,authManager.getIdUsuarioActual())
            .whereLessThan(CLAVE_FECHA_ENCUENTRO,getTiempoActual())

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->

                if (error != null || valor == null) {
                    return@EventListener
                }



                if (valor.isEmpty) {

                    valoresEncuentrosConcluidos.postValue(emptyList())

                } else {

                    val encuentros = ArrayList<Encuentro>()

                    for (doc in valor) {
                        val encuentro = doc.toObject(Encuentro::class.java)
                        encuentros.add(encuentro)
                    }

                    valoresEncuentrosConcluidos.postValue(encuentros)
                }
            })

    }



    fun agregarComentario(
        puntuacion: Float,
        descripcion:String,
        id_encuentro: String,
        id_usuario: String,
        enAcciondeExito: () -> Unit, enAcciondeFracaso: () -> Unit ){

        val referenciaDocumento = baseDeDato.collection(COLECCION_COMENTARIOS).document()

        val comentario = HashMap<String,Any>()

        comentario[CLAVE_ID_COMENTARIO] = referenciaDocumento.id
        comentario[CLAVE_PUNTUACION_COMENTARIO] = puntuacion
        comentario[CLAVE_DESCRIPCION_COMENTARIO] = descripcion
        comentario[CLAVE_FECHA_COMENTARIO] = getTiempoActual()
        comentario[CLAVE_ID_ENCUENTRO_COMENTARIO] = id_encuentro
        comentario[CLAVE_ID_USUARIO_COMENTARIO] = id_usuario



        referenciaDocumento
            .set(comentario)
            .addOnSuccessListener { enAcciondeExito() }
            .addOnFailureListener { enAcciondeFracaso() }
    }



    fun listenerCambiosDeValorComentarios(idEncuentro: String): LiveData<List<Comentario>>{
        listenerDeValoresComentarios(idEncuentro)
        return valoresComentarios
    }

    private fun listenerDeValoresComentarios(idEncuentro: String) {

        registrosComentarios = baseDeDato.collection(COLECCION_COMENTARIOS)
            .whereEqualTo(CLAVE_ID_ENCUENTRO_COMENTARIO,idEncuentro)

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->

                if (error != null || valor == null) {
                    return@EventListener
                }



                if (valor.isEmpty) {

                    valoresComentarios.postValue(emptyList())

                } else {

                    val comentarios = ArrayList<Comentario>()

                    for (doc in valor) {
                        val comentario = doc.toObject(Comentario::class.java)
                        comentarios.add(comentario)
                    }

                    valoresComentarios.postValue(comentarios)
                }
            })

    }



    fun listenerCambiosDeValorEncuentrosByID(idCancha: String): LiveData<List<Encuentro>>{
        escucharPorCambiosEnValoresEncuentrosByIdCancha(idCancha)
        return valoresEncuentrosByIdCancha
    }


    private fun escucharPorCambiosEnValoresEncuentrosByIdCancha(id_cancha: String) {


        registrosEncuentrosByIdCancha = baseDeDato.collection(COLECCION_ENCUENTROS)
            .whereEqualTo(CLAVE_ID_CANCHA_ENCUENTRO,id_cancha)

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->

                if (error != null || valor == null) {
                    return@EventListener
                }

                if (valor.isEmpty) {

                    valoresEncuentrosByIdCancha.postValue(emptyList())

                } else {

                    val encuentros = ArrayList<Encuentro>()

                    for (doc in valor) {
                        val encuentro = doc.toObject(Encuentro::class.java)
                        encuentros.add(encuentro)
                    }

                    valoresEncuentrosByIdCancha.postValue(encuentros)
                }
            })

    }

    fun listenerCambiosDevalor_P_EncuentrosById(idEncuentro: String): LiveData<List<P_Encuentro>>{
        escucharPorCambiosEnValores_P_EncuentrosByIdCancha(idEncuentro)
        return valores_P_Encuentros
    }

    private fun escucharPorCambiosEnValores_P_EncuentrosByIdCancha(idEncuentro: String) {
        registros_P_Encuentros= baseDeDato.collection(COLECCION_P_ENCUENTROS)
            .whereEqualTo(CLAVE_ID_P_ENCUENTRO,idEncuentro)

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->

                if (error != null || valor == null) {
                    return@EventListener
                }

                if (valor.isEmpty) {

                    valores_P_Encuentros.postValue(emptyList())

                } else {

                    val p_encuentros = ArrayList<P_Encuentro>()

                    for (doc in valor) {
                        val participante = doc.toObject(P_Encuentro::class.java)
                        p_encuentros.add(participante)
                    }

                    valores_P_Encuentros.postValue(p_encuentros)
                }
            })
    }


    private fun getTiempoActual() = System.currentTimeMillis()



}