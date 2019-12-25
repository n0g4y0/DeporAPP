package io.github.n0g4y0.deporapp.ui.equipo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_crear_equipo.*


class CrearEquipoFragment : Fragment(R.layout.fragment_crear_equipo) {


    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       btn_guardar_equipo.setOnClickListener {

            guardarDatosEquipo()
       }
    }

    private fun guardarDatosEquipo() {


        val nombre_cancha = ed_nombre_equipo.text.toString().trim()
        val descripcion_cancha = ed_descripcion_equipo.text.toString().trim()
        val futbol = sw_futbol.isChecked
        val futsal = sw_futsal.isChecked
        val basquet = sw_basquet.isChecked
        val voley = sw_voley.isChecked


        AlertDialog.Builder(requireContext())
            .setMessage("Desea Guardar Equipo?")
            .setPositiveButton("OK"){ _,_ ->

                deporappViewModel?.let {
                    it.agregarEquipo(nombre_cancha,descripcion_cancha,futbol,futsal,basquet,voley)
                    findNavController().popBackStack(R.id.listaEquipoFragment,false)

                }
            }
            .setNegativeButton("Cancelar",null)
            .create().show()



    }


}
