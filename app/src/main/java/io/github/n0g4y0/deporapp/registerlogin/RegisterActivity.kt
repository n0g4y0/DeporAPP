package io.github.n0g4y0.deporapp.registerlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.github.n0g4y0.deporapp.MainActivity
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    // variables CONSTANTES
    companion object {
        val NAME_ACTIVITY:String = "Register Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.title = NAME_ACTIVITY


        // funcionalidad del edittext, nos llega al ACTIVITY de LOGEO, si ya tenemos cuenta:
        existe_cuenta_textview.setOnClickListener {
            val myintent = Intent(this,LoginActivity::class.java)
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

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // agregamos una salida en consola si fue creado:
                Log.d(NAME_ACTIVITY,"el usuario fue creado con exito, tiene el ID: ${it.result?.user?.uid}")
                saveToUserFirebaseDatabase(it.toString())
            }
            .addOnFailureListener {
                Log.d(NAME_ACTIVITY,"fallo al crear al Usuario ${it.message}")
                Toast.makeText(this,"fallo al crear al usuario: ${it.message}",Toast.LENGTH_SHORT).show()

            }
    }

    /*
    * funcion para guardar estos datos en la base de datos de FIREBASE:
    * */

    private fun saveToUserFirebaseDatabase(prueba:String){

        // obtenemos el id del usuario actual:
        val uid = FirebaseAuth.getInstance().uid ?: ""
        // creando una instancia de la base de datos de FIREBASE:
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        // creo una variables que contendra al usuario:
        val user = User(uid,username_edittext_register.text.toString())

        // modificamos la referencia de la BD de FIREBASE:
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(NAME_ACTIVITY,"finalmente guardamos al usuario en la BD de FIREBASE")

                // iniciamos el activity principal:
                val intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d(NAME_ACTIVITY,"hubo problemas en guardar en la BD: ${it.message}")
            }

    }
}
