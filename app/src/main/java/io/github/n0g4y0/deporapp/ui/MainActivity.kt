package io.github.n0g4y0.deporapp.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.databinding.ActivityMainBinding
import io.github.n0g4y0.deporapp.databinding.NavHeaderMainBinding
import io.github.n0g4y0.deporapp.model.User
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.lang.IllegalArgumentException


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private var deporappViewModel : DeporappViewModel? = null

    // variable que controla la navegacion
    private val controladorNav by lazy { findNavController(R.id.nav_host_fragment) }

    private val appBarConfiguration by lazy {

        AppBarConfiguration(
            setOf(R.id.anuncioFragment), drawer_layout
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

        //agrega el diseño HEADER a la cabecera del NAVIGATIONDRAWER:
        activityMainBinding.navView.addHeaderView(headerBinding.root)

    }

    private fun configurarNavegacion() {

        NavigationUI.setupActionBarWithNavController(this, controladorNav, drawer_layout)

        NavigationUI.setupWithNavController(toolbar,controladorNav,appBarConfiguration)




    }

    /*
    *
    * Se usara un archivo ViewModel comun, para todos los Fragments:
    * */
    private fun configurarViewModel() {
        try {
            // esta linea de codigo, asegura que utilizaremos este archivo para guardar datos, mientras dure la APP.
            val viewModelProvider = ViewModelProvider(
                        controladorNav.getViewModelStoreOwner(R.id.nav_graph),
                        ViewModelProvider.AndroidViewModelFactory(application))

            deporappViewModel = viewModelProvider.get(DeporappViewModel::class.java)

            //linea muy util, si queremos editar la cabecera, no lo utilizaremos:
            //headerBinding.viewModel = deporappViewModel


        }catch (e: IllegalArgumentException){
            e.printStackTrace()
        }
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

            AlertDialog.Builder(this)
                .setMessage("Salir?")
                .setPositiveButton("OK"){ _,_ ->
                    deporappViewModel?.let {
                        it.cerrarSession()
                        super.onBackPressed()
                        moveTaskToBack(true) // esta linea cierra todos los activities.
                    }

                }

                .setNegativeButton("Cancelar",null)
                .create().show()

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

                controladorNav.navigate(R.id.mapaFragment)
            }
            R.id.nav_my_games -> {

            }
            R.id.nav_courts -> {

                controladorNav.navigate(R.id.listaCanchasFragment)

            }
            R.id.nav_new_team -> {

                controladorNav.navigate(R.id.listaEquipoFragment)

            }
            R.id.nav_anuncios -> {

                controladorNav.popBackStack(R.id.anuncioFragment,false)

            }
            R.id.nav_logout -> {

                cerrarSesion()

            }

            R.id.nav_about_us -> {

                controladorNav.navigate(Uri.parse("deporapp://acerca"))

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun cerrarSesion(){

        AlertDialog.Builder(this)
            .setMessage("Cerrar Sesion?")
            .setPositiveButton("OK"){ _,_ ->

                deporappViewModel?.let {
                    it.cerrarSession()
                    finish()
                }

            }

            .setNegativeButton("Cancelar",null)
            .create().show()

    }

}

