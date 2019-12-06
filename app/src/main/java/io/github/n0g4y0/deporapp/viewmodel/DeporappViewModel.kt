package io.github.n0g4y0.deporapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager
import java.io.File


class DeporappViewModel(val app: Application): AndroidViewModel(app) {

    private val authManager by lazy { AutentificacionManager() }

    val ubicacion : MutableLiveData<LatLng> = MutableLiveData()

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


    fun cerrarSession(){

        authManager.cerrarSesion(this.app)

    }

    fun onCapturaClick(){

    }




}