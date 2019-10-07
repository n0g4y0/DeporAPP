package io.github.n0g4y0.deporapp.registerlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import io.github.n0g4y0.deporapp.activities.MainActivity
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.utils.login
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        // accion que hara cuando presionamos el boton de regresar al inicio, finalizamos el presente ACTIVITY:
       /* back_to_register_textview.setOnClickListener {
                //finaliza un ACTIVITY:
                //finish()
        }*/

        // accion cuando presionamos el boton de LOGUEO:
        login_button_login.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin(){
        val email = email_edittext_login.text.toString()
        val password = password_edittext_login.text.toString()

        if (email.isEmpty() && password.isEmpty()){
            Toast.makeText(this,"Por favor llene los espacios",Toast.LENGTH_SHORT).show()
            return
        }

        // hacemos la consulta, si el usuario esta validado en el AUTH del FIREBASE:
        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Log.d("login","correctamente Conectado: ${it.result?.user?.uid}")

                login()

            }
            .addOnFailureListener {
                Toast.makeText(this,"Datos Incorrectos: ${it.message}",Toast.LENGTH_SHORT).show()
            }

    }
    /*
    * funcion que evita que nos volvamos a LOGEAR, ya cargara la sesion
    * */
    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.let {
            login()
        }
    }
}
