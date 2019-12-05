package io.github.n0g4y0.deporapp.ui.cancha

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.gms.maps.model.LatLng
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.ui.mapa.EnviarUbicacionFragment
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_crear_cancha.*
import java.io.File

private const val TAG = "CanchaFragment"
private const val ARG_CANCHA_ID = "cancha_id"
private const val FECHA_DIALOGO = "fechaDialogo"
private const val FECHA_SOLICITUD = 0
private const val FOTO_SOLICITUD = 2

//ponemos el diseño en el constructor, para evitar sobreESCRIBIR la funcion ONCREATEVIEW()

class CrearCanchaFragment : Fragment(R.layout.fragment_crear_cancha){

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        capturarUbicacion()

        cancha_ubicacion.setOnClickListener {

            findNavController().navigate(R.id.enviarUbicacionFragment)



        }


    }

    private fun capturarUbicacion() {
        deporappViewModel.ubicacion.observe(this, Observer { ubicacion ->
            cancha_ubicacion.text = ubicacion.toString()
        })
    }


}