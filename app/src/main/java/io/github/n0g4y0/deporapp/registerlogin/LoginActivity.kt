package io.github.n0g4y0.deporapp.registerlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import io.github.n0g4y0.deporapp.MainActivity
import io.github.n0g4y0.deporapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // accion que hara cuando presionamos el boton de regresar al inicio, finalizamos el presente ACTIVITY:
        back_to_register_textview.setOnClickListener {
                finish()
        }

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
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Log.d("login","correctamente Conectado: ${it.result?.user?.uid}")

                val intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            .addOnFailureListener {
                Toast.makeText(this,"Datos Incorrectos: ${it.message}",Toast.LENGTH_SHORT).show()
            }


    }
}
