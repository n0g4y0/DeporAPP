package io.github.n0g4y0.deporapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.fragments.MapaFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_menu.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{


    /*
    * FUNCION PRINCIPAL DE LA APLICACION:
    *
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val navController = Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupWithNavController(nav_view, navController)
        NavigationUI.setupActionBarWithNavController(
            this,
            navController,
            drawer_layout
            )

    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
        return NavigationUI.navigateUp(
            Navigation.findNavController(this,R.id.fragment),
            drawer_layout
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return true
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


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

}

