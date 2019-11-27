package io.github.n0g4y0.deporapp.ui

import android.app.Activity

/*
* esta clase sirve para iniciar ACTIVIDADES, como un enrutador que iniciara actividades
*
* */

class Enrutador {

    fun iniciarMenuPrincipal(activity: Activity){

        val intent = MainActivity.crearIntent(activity)
        activity.startActivity(intent)

    }
}