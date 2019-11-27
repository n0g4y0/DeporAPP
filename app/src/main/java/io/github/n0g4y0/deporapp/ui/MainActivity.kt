package io.github.n0g4y0.deporapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.ui.login.RegisterActivity
import io.github.n0g4y0.deporapp.ui.mapa.MapaFragment
import io.github.n0g4y0.deporapp.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_menu.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    // variables globales
    companion object {
        // variable para el usuario ACTUAL:
        var currentUser: User? = null
        fun crearIntent(context: Context) = Intent(context, MainActivity::class.java)

    }


    /*
     * a partir de aqui, TODOS LOS CAMBIOS POSIBLES PARA EL NAVIGATION DRAWER:
     * */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_new_game -> {
                // Handle the camera action
            }
            R.id.nav_cercanos -> {

                // empezando a usar el FRAGMENT MANAGER:
                val FragmentActual = supportFragmentManager.findFragmentById(R.id.contenedor_de_fragments)

                if (FragmentActual == null){

                    val fragment = MapaFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.contenedor_de_fragments,fragment)
                        .addToBackStack(null)
                        .commit()
                }

            }
            R.id.nav_my_games -> {

            }
            R.id.nav_courts -> {

            }
            R.id.nav_new_team -> {

            }
            R.id.nav_logout -> {

            }
            R.id.nav_profile -> {

            }
            R.id.nav_about_us -> {

                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Acerca De")
                    .setMessage(R.string.descripcion_texview_about_fragment)
                    .setPositiveButton("OK", null)
                    .create()
                    .show()

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    /*
    * FUNCION PRINCIPAL DE LA APLICACION:
    *
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // PRUEBAS PARA TRAER FARGMENTS:



        // para el NAVIGATION DRAWER:
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

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

