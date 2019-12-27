package io.github.n0g4y0.deporapp.ui.encuentro


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_crear_juego.*
import java.text.SimpleDateFormat
import java.util.*


class CrearJuegoFragment : Fragment(R.layout.fragment_crear_juego) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    var fechaActual: Date? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFechaActualEncuentro()
        et_fecha_encuentro.setOnClickListener {
            findNavController().navigate(R.id.datePickerFragment)
        }
    }

    private fun getFechaActualEncuentro(){

        deporappViewModel.fechaEncuentro.observe(this, Observer { fechaActualEncuentro ->
                fechaActual = fechaActualEncuentro
                et_fecha_encuentro.setText(convertirFechaAString(fechaActual!!))


        })

    }

    private fun convertirFechaAString(fecha: Date): String{

        var formato: String = "yyyy-MM-dd"

        val formatter = SimpleDateFormat(formato, Locale.US)

        return formatter.format(fecha)
    }




}
