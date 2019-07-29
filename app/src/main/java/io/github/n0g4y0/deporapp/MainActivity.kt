package io.github.n0g4y0.deporapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.github.n0g4y0.deporapp.model.User
import io.github.n0g4y0.deporapp.registerlogin.RegisterActivity

class MainActivity : AppCompatActivity() {

    // variables globales
    companion object {
        // variable para el usuario ACTUAL:
        var currentUser: User? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // traer al usuario actual:
        fetchCurrentUser()

        // verifica si el usuario esta conectado correctamente:
        verifyUserIsLoggedIn()


    }

    private fun fetchCurrentUser(){

        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(User::class.java)
                Log.d("LatestMessage","Usuario Actual: ${currentUser?.username}")
                // muestra en la cabecera de arriba, el nombre del usuario actual
                supportActionBar?.title = "Usuario: ${currentUser.toString()}"

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

    }

    private fun verifyUserIsLoggedIn(){

        val uid = FirebaseAuth.getInstance().uid
        if (uid == null){
            val intent = Intent(this, RegisterActivity::class.java)
            // verificamos que no haya otro intent corriendo:
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }

}
