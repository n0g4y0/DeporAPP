package io.github.n0g4y0.deporapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager


class DeporappViewModel(val app: Application): AndroidViewModel(app) {

    private val authManager by lazy { AutentificacionManager() }


    fun cerrarSession(){

        authManager.cerrarSesion(this.app)

    }


}