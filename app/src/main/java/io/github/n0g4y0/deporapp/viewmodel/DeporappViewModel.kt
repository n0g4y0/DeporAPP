package io.github.n0g4y0.deporapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager
import io.github.n0g4y0.deporapp.firebase.corutinas.ConsultasRepositorio
import io.github.n0g4y0.deporapp.firebase.corutinas.ConsultasRepositorioImpl
import io.github.n0g4y0.deporapp.firebase.corutinas.Result
import io.github.n0g4y0.deporapp.firebase.firestore.FirestoreManager
import io.github.n0g4y0.deporapp.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import kotlin.coroutines.CoroutineContext


class DeporappViewModel(val app: Application) : AndroidViewModel(app), CoroutineScope {

    /*
    * variables para trabajar con corutinas:
    *
    * */
    val consultasRepositorio: ConsultasRepositorio = ConsultasRepositorioImpl()

    // configurar el contexto de la corutina:
    private val compositeJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + compositeJob

    // corutina JOBs
    private var getUsuarioJob: Job? = null
    private var crearEncuentroJob: Job? = null
    private var preguntarUsuarioEnComentarioJob: Job? = null
    private var getCanchaJob: Job? = null
    private var participanteJob: Job? = null
    private var disminuirParticipanteJob: Job? = null

    //live-data

    private val _codigo_texto_a_mostrar = MutableLiveData<Int>()
    val codigo_texto_a_mostrar: LiveData<Int> = _codigo_texto_a_mostrar

    private val _usuarioConsultado = MutableLiveData<Usuario>()
    val usuarioConsultado: LiveData<Usuario> = _usuarioConsultado


    private val _comentoElUsuario = MutableLiveData<Boolean>()
    val comentoElUsuario: LiveData<Boolean> = _comentoElUsuario


    private val _canchaConsultada = MutableLiveData<Cancha>()
    val canchaConsultada: LiveData<Cancha> = _canchaConsultada

    private val _equipoConsultado = MutableLiveData<Equipo>()
    val equipoConsultado: LiveData<Equipo> = _equipoConsultado

    private val _disminuirParticipante = MutableLiveData<Boolean>()
    val disminuirParticipante: LiveData<Boolean> = _disminuirParticipante


    // traer al usuario con determinado ID:
    fun traerAlUsuarioDesdeFirestore(idUsuario: String) {
        if (getUsuarioJob?.isActive == true) getUsuarioJob?.cancel()

        getUsuarioJob = launch {
            when (val resultado = consultasRepositorio.getUsuarioPorID(idUsuario)) {

                is Result.Success -> _usuarioConsultado.value = resultado.data

                is Result.Error -> _codigo_texto_a_mostrar.value = R.string.error_firestore

                is Result.Canceled -> _codigo_texto_a_mostrar.value =
                    R.string.cancelar_proceso_firestore

            }
        }

    }

    fun traerEquipoDesdeFirestore(id_equipo: String) {
        if (participanteJob?.isActive == true) participanteJob?.cancel()

        participanteJob = launch {
            when (val resultado = consultasRepositorio.getEquipoPorId(id_equipo)) {

                is Result.Success -> _equipoConsultado.value = resultado.data

                is Result.Error -> _codigo_texto_a_mostrar.value = R.string.error_firestore

                is Result.Canceled -> _codigo_texto_a_mostrar.value =
                    R.string.cancelar_proceso_firestore

            }
        }
    }

    fun traerCancharDesdeFirestrore(idCancha: String) {


        if (getCanchaJob?.isActive == true) getCanchaJob?.cancel()

        getCanchaJob = launch {


            when (val resultado = consultasRepositorio.getCanchaPorId(idCancha)) {

                is Result.Success -> _canchaConsultada.value = resultado.data

                is Result.Error -> _codigo_texto_a_mostrar.value = R.string.error_firestore

                is Result.Canceled -> _codigo_texto_a_mostrar.value =
                    R.string.cancelar_proceso_firestore

            }

        }
    }

    // crear usuario en firestore, por medio de corutinas:

    fun crearComentarioEnFirestoreConHilos(
        puntuacion: Float,
        descripcion: String,
        id_encuentro: String,
        id_usuario: String,
        apodo_usuario: String
    ) {

        val fechaActual = System.currentTimeMillis()

        val id = UUID.randomUUID().toString()
        val comentario = Comentario(
            id = id,
            puntuacion = puntuacion,
            descripcion = descripcion,
            fecha = fechaActual,
            id_encuentro = id_encuentro,
            id_usuario = id_usuario,
            apodo_usuario = apodo_usuario
        )

        if (crearEncuentroJob?.isActive == true) crearEncuentroJob?.cancel()
        crearEncuentroJob = launch {
            when (consultasRepositorio.crearComentario(comentario)) {

                is Result.Success -> _codigo_texto_a_mostrar.value =
                    R.string.comentario_creado_firestore_exitosamente

                is Result.Error -> _codigo_texto_a_mostrar.value =
                    R.string.comentario_creado_firestore_error

                is Result.Canceled -> _codigo_texto_a_mostrar.value =
                    R.string.comentario_creado_firestore_cancelado

            }
        }


    }

    fun elUsuarioCalificoEncuentro(id_encuentro: String, id_usuario: String) {

        if (preguntarUsuarioEnComentarioJob?.isActive == true) preguntarUsuarioEnComentarioJob?.cancel()


        preguntarUsuarioEnComentarioJob = launch {

            when (val dato =
                consultasRepositorio.esteUsuarioYaComentoEncuentro(id_encuentro, id_usuario)) {

                is Result.Success -> _comentoElUsuario.value = dato.data

                is Result.Error -> _codigo_texto_a_mostrar.value = R.string.msj_default_error

                is Result.Canceled -> _codigo_texto_a_mostrar.value = R.string.msj_default_cancelado

            }
        }

    }

    fun disminuirCantParticipantes(idEncuentro: String): Boolean{

        if (disminuirParticipanteJob?.isActive == true) disminuirParticipanteJob?.cancel()


        disminuirParticipanteJob = launch {

            when (val dato =
                consultasRepositorio.disminuirParticipante(idEncuentro)) {

                is Result.Success -> _disminuirParticipante.value = dato

                is Result.Error -> _codigo_texto_a_mostrar.value = R.string.msj_default_error

                is Result.Canceled -> _codigo_texto_a_mostrar.value = R.string.msj_default_cancelado

            }
        }

    }

    // funciones para manejar la creacion de participantes de los encuentros y equipos

    fun crearP_EncuentroConHilos(
        id_encuentro: String,
        nombreUsuario: String,
        photoUrl: String,
        id_usuario_actual: String
    ) {

        val id = UUID.randomUUID().toString()
        val p_encuentro = P_Encuentro(
            id = id,
            id_encuentro = id_encuentro,
            nombre_usuario = nombreUsuario,
            photo_url = photoUrl,
            id_usuario_actual = id_usuario_actual
        )

        if (participanteJob?.isActive == true) participanteJob?.cancel()
        participanteJob = launch {
            when (consultasRepositorio.crear_P_encuentro(p_encuentro)) {

                is Result.Success -> _codigo_texto_a_mostrar.value =
                    R.string.p_encuentro_creado_firestore_exitosamente

                is Result.Error -> _codigo_texto_a_mostrar.value =
                    R.string.p_encuentro_creado_firestore_error

                is Result.Canceled -> _codigo_texto_a_mostrar.value =
                    R.string.p_encuentro_creado_firestore_cancelado

            }
        }

    }

    fun crearP_EquipoConHilos(id_equipo: String, nombreEquipo: String, id_usuario_actual: String) {

        val id = UUID.randomUUID().toString()
        val p_Equipo = P_Equipo(
            id = id,
            id_equipo = id_equipo,
            nombre_equipo = nombreEquipo,
            id_usuario_actual = id_usuario_actual,
            nombre_usuario = getNombreUsuarioActual(),
            photo_usuario = getPhotoUrlUsuarioActual()
        )

        if (participanteJob?.isActive == true) participanteJob?.cancel()
        participanteJob = launch {
            when (consultasRepositorio.crear_P_equipo(p_Equipo)) {

                is Result.Success -> _codigo_texto_a_mostrar.value =
                    R.string.p_equipo_creado_firestore_exitosamente

                is Result.Error -> _codigo_texto_a_mostrar.value =
                    R.string.p_equipo_creado_firestore_error

                is Result.Canceled -> _codigo_texto_a_mostrar.value =
                    R.string.p_equipo_creado_firestore_cancelado

            }
        }

    }


    /*
    * VARIABLES GLOBALES
    * */

    private val authManager = AutentificacionManager()

    private val firestore = FirestoreManager()


    var ubicacion: MutableLiveData<LatLng> = MutableLiveData()

    var ruta_foto_local: MutableLiveData<String> = MutableLiveData()

    private var deberiaBorrarImagen: Boolean = true

    var archivoImagenTemporal: File? = null
        set(value) {
            borrarFotoTemporal()
            field = value
            deberiaBorrarImagen = true
        }

    var fechaEncuentro: MutableLiveData<Date> = MutableLiveData()
    var horaEncuentro: MutableLiveData<Date> = MutableLiveData()


    var idCanchaEncuentro: MutableLiveData<Cancha> = MutableLiveData()

    var idEquipoEncuentro: MutableLiveData<P_Equipo> = MutableLiveData()


    var fotoPerfilUsuarioActual = ObservableField("")
    var nombreUsuarioActual = ObservableField("")
    var correoUsuarioActual = ObservableField("")


    /*
    * Funciones:
    *
    * */


    private fun borrarFotoTemporal() {
        if (deberiaBorrarImagen) {
            archivoImagenTemporal?.let {
                if (it.exists()) it.delete()
            }
        }
    }


    fun ubicacionSeleccionada(nuevaUbicacion: LatLng) {
        ubicacion.value = nuevaUbicacion
    }

    fun setRutaFotoActual(nuevo: String) {
        ruta_foto_local.value = nuevo
    }


    fun cerrarSession() {

        authManager.cerrarSesion(this.app)

    }

    fun getListaCanchas(): LiveData<List<Cancha>> {

        return firestore.enCambiosDeValorCanchas()

    }

    fun getListaAnuncios(): LiveData<List<Anuncio>> {
        return firestore.cambiosDeValorAnuncios()
    }

    fun getListaEquipos(): LiveData<List<Equipo>> {

        return firestore.cambiosDeValorEquipos()

    }

    fun getLista_p_equipos(): LiveData<List<P_Equipo>> {
        return firestore.cambiosDeValor_P_Equipos(getIdUsuarioActual())
    }

    fun agregarEquipo(
        nombre: String,
        descripcion: String,
        siFutbol: Boolean,
        siFutsal: Boolean,
        siBasquet: Boolean,
        siVoley: Boolean
    ) {

        return firestore.agregarEquipo(
            nombre,
            descripcion,
            siFutbol,
            siFutsal,
            siBasquet,
            siVoley,
            ::agregadoExitoso,
            ::agregadoFallido
        )

    }

    /*
    * el agregado mas importante, los encuentros deportivos:
    * */

    fun agregarEncuentro(
        nombre: String,
        idCancha: String,
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
        id_equipo: String
    ) {

        return firestore.agregarEncuentro(
            nombre,
            idCancha,
            fecha,
            hora,
            cupos,
            nota,
            deporte,
            esPrivado,
            fk_cancha_lat,
            fk_cancha_lng,
            fk_usuario_nick,
            fk_usuario_foto_url,
            id_equipo,
            ::agregadoExitoso,
            ::agregadoFallido
        )

    }

    fun getListaEncuentros(): LiveData<List<Encuentro>> {

        return firestore.cambiosDeValorEncuentros()

    }

    fun getListaEncuentrosPendientes(): LiveData<List<Encuentro>> {

        return firestore.listenerCambiosDeValorEncuentrosPendientes()

    }

    fun getListaEncuentrosConcluidos(): LiveData<List<Encuentro>> {

        return firestore.listenerCambiosDeValorEncuentrosConcluidos()

    }

    fun agregarComentario(
        puntuacion: Float,
        descripcion: String,
        id_encuentro: String,
        id_usuario: String
    ) {

        return firestore.agregarComentario(
            puntuacion,
            descripcion,
            id_encuentro,
            id_usuario,
            ::agregadoExitoso,
            ::agregadoFallido
        )
    }

    fun getListaComentariosEncuentro(idEncuentro: String): LiveData<List<Comentario>> {

        return firestore.listenerCambiosDeValorComentarios(idEncuentro)
    }

    fun getListaEncuentrosByIDCancha(idCancha: String): LiveData<List<Encuentro>> {

        return firestore.listenerCambiosDeValorEncuentrosByID(idCancha)

    }

    fun getLista_P_encuentroById(idEncuentro: String): LiveData<List<P_Encuentro>> {
        return firestore.listenerCambiosDevalor_P_EncuentrosById(idEncuentro)
    }

    fun getLista_P_equipoById(idEquipo: String) : LiveData<List<P_Equipo>>{
        return firestore.listenerCambiosDevalor_P_EquipoById(idEquipo)
    }


    // mensajes exito o fracaso, subida al FIRESTORE

    private fun agregadoExitoso() {
        //showToast(getString(R.string.posted_successfully))

    }

    private fun agregadoFallido() {
        //showToast(getString(R.string.post_add_error))
    }


    fun setFechaEncuentro(nuevaFecha: Date) {
        fechaEncuentro.value = nuevaFecha
    }

    fun setHoraEncuentro(nuevaHora: Date) {
        horaEncuentro.value = nuevaHora

    }

    fun setIdCanchaEncuentro(nuevoId: Cancha) {
        idCanchaEncuentro.value = nuevoId
    }

    fun setidEquipoEncuentro(nuevoId: P_Equipo) {
        idEquipoEncuentro.value = nuevoId
    }

    fun reiniciarLasVariablesMutables() {
        ubicacion.value = null
        ruta_foto_local.value = null
        fechaEncuentro.value = null
        horaEncuentro.value = null
        idCanchaEncuentro.value = null

    }

    fun getIdUsuarioActual(): String {
        return authManager.getIdUsuarioActual()
    }

    fun getNombreUsuarioActual(): String {
        return authManager.getNombreUsuarioActual()
    }

    fun getPhotoUrlUsuarioActual(): String {
        return authManager.getFotoUrlUsuarioActual().toString()
    }

    fun getCorreoUsuarioActual(): String {
        return authManager.getCorreoUsuarioActual()!!
    }

    fun getApodoUsuarioActual(): String {
        return getCorreoUsuarioActual().substringBefore('@')
    }


    fun cargarPerfil() {

        nombreUsuarioActual.set(getNombreUsuarioActual())
        correoUsuarioActual.set(getCorreoUsuarioActual())
        fotoPerfilUsuarioActual.set(getPhotoUrlUsuarioActual())
    }


}