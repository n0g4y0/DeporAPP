package io.github.n0g4y0.deporapp.ui.cancha

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.databinding.FragmentCrearCanchaBinding
import io.github.n0g4y0.deporapp.util.SeleccionArchivo
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_crear_cancha.*

private const val TAG = "CanchaFragment"
private const val ARG_CANCHA_ID = "cancha_id"
private const val FECHA_DIALOGO = "fechaDialogo"
private const val FECHA_SOLICITUD = 0
private const val FOTO_SOLICITUD = 2

//ponemos el diseño en el constructor, para evitar sobreESCRIBIR la funcion ONCREATEVIEW()

class CrearCanchaFragment : Fragment(R.layout.fragment_crear_cancha){

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val seleccionArchivo : SeleccionArchivo by lazy { SeleccionArchivo(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCrearCanchaBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_crear_cancha,container,false)

        binding.viewModelnav = deporappViewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancha_foto.setOnClickListener {
            clickALaFoto()
        }

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


    /*
    * funciones para manipular las capturas de la camara:
    * */

    fun clickALaFoto(){

        val tomarFotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (tomarFotoIntent.resolveActivity(requireContext().packageManager) != null) {

            val archivo = seleccionArchivo.crearArchivoTemporal()
            deporappViewModel.archivoImagenTemporal = archivo
            val UriDeFoto = seleccionArchivo.UriDelArchivo(archivo)
            tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, UriDeFoto)
            startActivityForResult(tomarFotoIntent, SOLICITUD_CODIGO_CAMERA)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == SOLICITUD_CODIGO_CAMERA){
            val UriFoto = "file://${deporappViewModel.archivoImagenTemporal?.absolutePath}"
                Log.d("valor",UriFoto)
        }
    }


    companion object {
        private const val SOLICITUD_CODIGO_CAMERA = 1
    }


}