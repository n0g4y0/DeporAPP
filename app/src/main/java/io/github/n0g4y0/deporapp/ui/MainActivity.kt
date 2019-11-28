package io.github.n0g4y0.deporapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.databinding.ActivityMainBinding
import io.github.n0g4y0.deporapp.databinding.NavHeaderMainBinding
import io.github.n0g4y0.deporapp.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private val controladorNav by lazy { findNavController(R.id.nav_host_fragment) }

    private val appBarConfiguration by lazy {

        AppBarConfiguration(
            setOf(R.id.anuncioFragment, R.id.mapaFragment), drawer_layout
        )

    }

    private lateinit var headerBinding : NavHeaderMainBinding



    // variables globales
    companion object {
        // variable para el usuario ACTUAL:
        var currentUser: User? = null
        fun crearIntent(context: Context) = Intent(context, MainActivity::class.java)

    }

    /*
    * FUNCION PRINCIPAL DE LA APLICACION:
    *
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configurarDataBinding()
        setSupportActionBar(toolbar)
        configurarNavegacion()
        configurarViewModel()
        configurarVistas()


    }

    private fun configurarDataBinding() {

        val activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        // mostrando el menu de usuario
        headerBinding = DataBindingUtil.inflate(layoutInflater,R.layout.nav_header_main, activityMainBinding.navView,false)

    }

    private fun configurarNavegacion() {

        NavigationUI.setupActionBarWithNavController(this, controladorNav, drawer_layout)

        NavigationUI.setupWithNavController(toolbar,controladorNav,appBarConfiguration)




    }

    private fun configurarViewModel() {
        // no joda
    }

    private fun configurarVistas() {
        nav_view.setNavigationItemSelectedListener(this)

    }

    /*
    * Esta funcion representa el BOTON BACK (ATRAS)
    * */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }




    /*
     * a partir de aqui, TODOS LOS CAMBIOS POSIBLES PARA EL NAVIGATION DRAWER:
     * */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_new_game -> {
                // Handle the camera action
            }
            R.id.nav_cercanos -> {


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

}

