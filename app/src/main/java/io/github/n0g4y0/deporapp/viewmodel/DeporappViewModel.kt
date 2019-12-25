package io.github.n0g4y0.deporapp.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager
import io.github.n0g4y0.deporapp.firebase.firestore.FirestoreManager
import io.github.n0g4y0.deporapp.firebase.storage.StorageManager
import io.github.n0g4y0.deporapp.model.Anuncio
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.model.Equipo
import java.io.File


class DeporappViewModel(val app: Application): AndroidViewModel(app) {


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

        return firestore.agregarEquipo(nombre,descripcion,siFutbol,siFutsal,siBasquet,siVoley, ::agregadoExitosoCancha, ::agregadoFallidoCancha)

    }






    // mensajes exito o fracaso, subida al FIRESTORE

    private fun agregadoExitosoCancha() {
        //showToast(getString(R.string.posted_successfully))

    }

    private fun agregadoFallidoCancha() {
        //showToast(getString(R.string.post_add_error))
    }




}