package io.github.n0g4y0.deporapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Usuario
import io.github.n0g4y0.deporapp.util.login
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth

    // variables CONSTANTES
    companion object {
        val NAME_ACTIVITY:String = "Register Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        supportActionBar?.title =
            NAME_ACTIVITY


        // funcionalidad del edittext, nos llega al ACTIVITY de LOGEO, si ya tenemos cuenta:
        existe_cuenta_textview.setOnClickListener {
            val myintent = Intent(this, LoginActivity::class.java)
            startActivity(myintent)
        }

        register_button.setOnClickListener {
            performRegister()
        }


    }

    private fun performRegister(){

        val email = email_edittext_register.text.toString()
        val password = password_edittext_register.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,"por favor introduzca algo en email/password", Toast.LENGTH_SHORT).show()
            return
        }

        // autentificacion con firebase, creando un usuario y contraseña:

        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // agregamos una salida en consola si fue creado:
                Log.d(NAME_ACTIVITY,"el usuario fue creado con exito, tiene el ID: ${it.result?.user?.uid}")
                saveToUserFirebaseDatabase()
            }
            .addOnFailureListener {
                Log.d(NAME_ACTIVITY,"fallo al crear al Usuario ${it.message}")
                Toast.makeText(this,"fallo al crear al usuario: ${it.message}",Toast.LENGTH_SHORT).show()

            }
    }

    /*
    * funcion para guardar estos datos en la base de datos de FIREBASE:
    * */

    private fun saveToUserFirebaseDatabase(){

        // obtenemos el id del usuario actual:
        val uid = FirebaseAuth.getInstance().uid ?: ""
        // creando una instancia de la base de datos de FIREBASE:
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        // creo una variables que contendra al usuario:
        val user = Usuario(uid,username_edittext_register.text.toString())

        // modificamos la referencia de la BD de FIREBASE:
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(NAME_ACTIVITY,"finalmente guardamos al usuario en la BD de FIREBASE")

                // iniciamos el activity principal:
                login()
            }
            .addOnFailureListener {
                Log.d(NAME_ACTIVITY,"hubo problemas en guardar en la BD: ${it.message}")
            }

    }

    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.let {
            login()
        }
    }
}
