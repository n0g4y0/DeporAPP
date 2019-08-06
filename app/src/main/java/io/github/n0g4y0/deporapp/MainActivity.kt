package io.github.n0g4y0.deporapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.github.n0g4y0.deporapp.model.User
import io.github.n0g4y0.deporapp.registerlogin.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import kotlinx.android.synthetic.main.content_menu.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    // variables globales
    companion object {
        // variable para el usuario ACTUAL:
        var currentUser: User? = null

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
        // para el NAVIGATION DRAWER:
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        // las siguientes lineas, nos ayudan a agregar TABS al menu principal (mediante FRAGMENTS):
        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)

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

/*
*  esta CLASE, viene a representar el manejo de FRAGMENTS, en modo de PAGINAS, usa la clase FragmentAdapter
* */

internal class MyPagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> {
                HomeFragment()
            }
            1 -> {
                GamesFragment()
            }
            else ->{
                return HomeFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Inicio"
            1 -> "Encuentros"
            else -> {
                return "Inicio"
            }

        }
    }

}
