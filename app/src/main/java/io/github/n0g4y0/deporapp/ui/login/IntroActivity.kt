package io.github.n0g4y0.deporapp.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.squareup.picasso.Picasso
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager
import io.github.n0g4y0.deporapp.firebase.auth.SOLICITUD_CODIGO_LOGIN
import io.github.n0g4y0.deporapp.ui.Enrutador
import io.github.n0g4y0.deporapp.util.showToast
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IntroActivity : AppCompatActivity() {

    private val enrutador by lazy { Enrutador() }
    private val autentificacionManager by lazy { AutentificacionManager() }

    companion object{
        fun crearIntent(contexto: Context) = Intent(contexto, IntroActivity::class.java)
    }


    private lateinit var botonLogin : Button
    private lateinit var botonRegistro : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // añadiendo la imagen PNG al ImageView:
        Picasso.get()
            .load(R.drawable.deporapp_intro_new)
            .resize(1000,1000)
            .onlyScaleDown()
            .centerCrop()
            .into(imagen_intro)

        botonLogin = findViewById(R.id.button_show_login)

        inicializar()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SOLICITUD_CODIGO_LOGIN){

            if (resultCode == Activity.RESULT_OK){

                GlobalScope.launch {
                    guardarUsuarioEnFirestore()
                }

                enrutador.iniciarMenuPrincipal(this)

            }else{
                showToast(getString(R.string.inicio_sesion_fallida))
            }

        }
    }



    private fun inicializar() {
        //setSupportActionBar()
        continuarAlMenuPrincipalSiUsuarioEstaLogeado()
        configurarLosListenersDeClicks()
    }


    private fun continuarAlMenuPrincipalSiUsuarioEstaLogeado(){

        if (estaElUsuarioLogeado()){
            enrutador.iniciarMenuPrincipal(this)
        }  else{
            Unit
        }
    }

    private suspend fun guardarUsuarioEnFirestore() {

        autentificacionManager.guardarUsuarioEnFirestore(this)
    }

    private fun configurarLosListenersDeClicks() {

        botonLogin.setOnClickListener { autentificacionManager.iniciarFlujoLogin(this) }

    }


    private fun estaElUsuarioLogeado() = autentificacionManager.elUsuarioInicioSesion()

}
