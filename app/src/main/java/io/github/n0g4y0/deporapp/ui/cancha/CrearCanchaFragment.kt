package io.github.n0g4y0.deporapp.ui.cancha

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.gms.maps.model.LatLng
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.firebase.firestore.FirestoreManager
import io.github.n0g4y0.deporapp.firebase.storage.StorageManager
import io.github.n0g4y0.deporapp.util.ImageUtils
import io.github.n0g4y0.deporapp.util.SeleccionArchivo
import io.github.n0g4y0.deporapp.util.mostrarMensaje
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

    private var ubicacionACtual: LatLng? = null

    private var urlImagenStorage : String? = null


    private val firestore by lazy { FirestoreManager() }
    private val storageManager by lazy { StorageManager() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_crear_cancha,container,false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancha_foto.setOnClickListener {
            capturarFotoGaleria()
        }

        capturarUbicacion()

        cancha_ubicacion.setOnClickListener {

            findNavController().navigate(R.id.enviarUbicacionFragment)
        }

        btn_guardar_cancha.setOnClickListener {

            continuarGuardadoEnFirestore()
        }


    }



    private fun guardandoElUriDelArchivo(url: String){

        urlImagenStorage = url

        Log.d("url imagen","pasa por la imagen: " + urlImagenStorage)

    }

    private fun continuarGuardadoEnFirestore() {

        Log.d("probando","pasa por aqui?")
        // ahora guardamos en la BD, de firestore:
        val titulo = cancha_titulo.text.toString().trim()
        Log.d("probando",titulo)

        val deporte_futbol = cancha_futbol8.isChecked
        val deporte_futsal = cancha_futsal.isChecked
        val deporte_basquet = cancha_basquet.isChecked
        val deporte_voley = cancha_voley.isChecked

        if (urlImagenStorage == null){

            urlImagenStorage = "imagen URL no disponible"
        }

        firestore.run {

            agregarCancha(
                titulo,
                urlImagenStorage!!,
                ubicacionACtual?.latitude!!,
                ubicacionACtual?.longitude!!,
                deporte_futbol,
                deporte_futsal,
                deporte_basquet,
                deporte_voley,
                false,
                ::agregadoExitosoCancha,
                ::agregadoFallidoCancha)

            mostrarMensaje(context!!,R.string.msj_crear_cancha_mensaje)
        }

        //limpiando el setText del boton ubicacion de cancha:
        cancha_ubicacion.setText(R.string.cancha_ubicacion_titulo)
        findNavController().popBackStack(R.id.listaCanchasFragment,false)

    }



    private fun agregadoExitosoCancha() {
        //showToast(getString(R.string.posted_successfully))

    }

    private fun agregadoFallidoCancha() {
        //showToast(getString(R.string.post_add_error))
    }



    private fun capturarUbicacion() {
        deporappViewModel.ubicacion.observe(this, Observer { ubicacion ->
            ubicacionACtual = ubicacion
            cancha_ubicacion.text = "Ubicacion Guardada"
        })
    }


    /*
    * funciones para manipular las capturas de la camara:
    * */

    fun capturarFotoGaleria(){

        val tomarFotoGaleriaIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        if (tomarFotoGaleriaIntent.resolveActivity(requireContext().packageManager) != null) {

            startActivityForResult(tomarFotoGaleriaIntent, SOLICITUD_CODIGO_CAMERA)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == SOLICITUD_CODIGO_CAMERA && data?.data != null){
            //val uriFoto = "file://${deporappViewModel.archivoImagenTemporal?.absolutePath}"
            val uriFotoGaleria = data.data
            val imagenBitmap = getImageConAutorizacion(uriFotoGaleria)
                actualizarImagenViewActual(imagenBitmap!!, uriFotoGaleria)
        }
    }

    private fun getImageConAutorizacion(uri: Uri): Bitmap? {

        return ImageUtils.transformarUriAlTamanio(
            uri,
            resources.getDimensionPixelSize(R.dimen.default_image_width),
            resources.getDimensionPixelSize(R.dimen.default_image_height),
            context!!)

    }



    private fun actualizarImagenViewActual(imagenBitmap: Bitmap,uriImagen: Uri) {

            cancha_foto.setImageBitmap(imagenBitmap)
            empezarSubidaImagenStorage(uriImagen)

    }

    private fun empezarSubidaImagenStorage(uri_imagen: Uri?) {

            if (uri_imagen != null){

                storageManager.subirFoto(uri_imagen, ::guardandoElUriDelArchivo)
            }

    }


    companion object {
        private const val SOLICITUD_CODIGO_CAMERA = 1

    }


}