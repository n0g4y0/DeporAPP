package io.github.n0g4y0.deporapp.firebase.auth

import android.app.Activity
import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

const val   SOLICITUD_CODIGO_LOGIN = 1000

/**
 *
 * Esta clase se ocupa de la autentificacion del usuario, en este caso utilizaremos una cuenta de GOOGLE
 *
 *
 * */

class AutentificacionManager {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val proveedores = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())


    fun iniciarFlujoLogin(activity: Activity){

            activity.startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(proveedores)
                        .setIsSmartLockEnabled(false)
                        .build(),

                SOLICITUD_CODIGO_LOGIN
            )
    }

    fun elUsuarioInicioSesion() = firebaseAuth.currentUser != null

    fun getUsuarioActual() = firebaseAuth.currentUser?.displayName ?: ""

    fun cerrarSesion(contexto: Context){

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val googleSignInClient = GoogleSignIn.getClient(contexto, googleSignInOptions)
        firebaseAuth.signOut()
        googleSignInClient.signOut()
    }

}