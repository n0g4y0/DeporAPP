package io.github.n0g4y0.deporapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager
import io.github.n0g4y0.deporapp.firebase.firestore.FirestoreManager
import io.github.n0g4y0.deporapp.model.Anuncio
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.model.Encuentro
import io.github.n0g4y0.deporapp.model.Equipo
import java.io.File
import java.util.*


class DeporappViewModel(val app: Application): AndroidViewModel(app) {

    /*
    * VARIABLES GLOBALES
    * */

    private val authManager by lazy { AutentificacionManager() }

    private val firestore by lazy { FirestoreManager() }


    var ubicacion : MutableLiveData<LatLng> = MutableLiveData()

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



    fun ubicacionSeleccionada(nuevaUbicacion: LatLng){
        ubicacion.value = nuevaUbicacion
    }

    fun setRutaFotoActual(nuevo: String){
        ruta_foto_local.value = nuevo
    }


    fun cerrarSession(){

        authManager.cerrarSesion(this.app)

    }

    fun getListaCanchas(): LiveData<List<Cancha>>{

        return firestore.enCambiosDeValorCanchas()

    }

    fun getListaAnuncios(): LiveData<List<Anuncio>>{
        return firestore.cambiosDeValorAnuncios()
    }

    fun getListaEquipos(): LiveData<List<Equipo>>{

        return firestore.cambiosDeValorEquipos()
        
    }
    fun agregarEquipo(nombre: String,
                      descripcion: String,
                      siFutbol: Boolean,
                      siFutsal: Boolean,
                      siBasquet: Boolean,
                      siVoley: Boolean
                      ){

        return firestore.agregarEquipo(nombre,descripcion,siFutbol,siFutsal,siBasquet,siVoley, ::agregadoExitoso, ::agregadoFallido)

    }

    /*
    * el agregado mas importante, los encuentros deportivos:
    * */

    fun agregarEncuentro(
        nombre: String,
        idCancha:String,
        fecha: Long,
        hora: Long,
        cupos: Int,
        nota: String,
        deporte: String,
        esPrivado: Boolean){

        return firestore.agregarEncuentro(nombre,idCancha,fecha,hora,cupos,nota,deporte,esPrivado,::agregadoExitoso, ::agregadoFallido)

    }
    fun getListaEncuentros(): LiveData<List<Encuentro>>{

        return firestore.cambiosDeValorEncuentros()

    }






    // mensajes exito o fracaso, subida al FIRESTORE

    private fun agregadoExitoso() {
        //showToast(getString(R.string.posted_successfully))

    }

    private fun agregadoFallido() {
        //showToast(getString(R.string.post_add_error))
    }


    fun setFechaEncuentro(nuevaFecha: Date){
        fechaEncuentro.value = nuevaFecha
    }

    fun setHoraEncuentro(nuevaHora: Date){
        horaEncuentro.value = nuevaHora

    }

    fun setIdCanchaEncuentro(nuevoId: Cancha){
        idCanchaEncuentro.value = nuevoId
    }

    fun reiniciarLasVariablesMutables(){
        ubicacion.value = null
        ruta_foto_local.value = null
        fechaEncuentro.value = null
        horaEncuentro.value = null
        idCanchaEncuentro.value = null

    }



}