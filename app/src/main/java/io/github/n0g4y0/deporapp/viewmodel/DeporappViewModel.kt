package io.github.n0g4y0.deporapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager


class DeporappViewModel(val app: Application): AndroidViewModel(app) {

    private val authManager by lazy { AutentificacionManager() }

    val ubicacion : MutableLiveData<LatLng> = MutableLiveData()

    fun ubicacionSeleccionada(nuevaUbicacion: LatLng){
        ubicacion.value = nuevaUbicacion
    }


    fun cerrarSession(){

        authManager.cerrarSesion(this.app)

    }


}