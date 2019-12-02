package io.github.n0g4y0.deporapp.ui.mapa


import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.databinding.FragmentEnviarUbicacionBinding
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_enviar_ubicacion.*

/**
 * clase para enviar una ubicacion
 */

private const val DEFAULT_ZOOM = 18f

class EnviarUbicacionFragment : DialogFragment() ,OnMapReadyCallback {



    private lateinit var vistaMapa: MapView

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private lateinit var map : GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val SOLICITAR_LOCALIZACION = 2

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEnviarUbicacionBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_enviar_ubicacion,container,false
        )

        binding.enviarMapa = deporappViewModel

        // lineas que controlan el mapa
        binding.enviarMapaView.onCreate(savedInstanceState)
        binding.enviarMapaView.onResume()
        binding.enviarMapaView.getMapAsync(this)


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {

            dialog?.setTitle("Enviar Ubicacion")
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        }

        boton_enviar.setOnClickListener {
            // asi llamo al toast
            Toast.makeText(activity,"hola",Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }


    override fun onMapReady(googleMap: GoogleMap?) {

        map = googleMap!!
        configurarLocalizacionCliente()
        getLocalizacionActual()

        // añadiendo una marca en bolivia:
        val cbba = LatLng(-17.3952935,-66.1484111)
        map.let {

            val update = CameraUpdateFactory.newLatLngZoom(cbba, 18.0f)

            it?.moveCamera(update)
            it?.addMarker(MarkerOptions().position(cbba).title("prueba"))

        }
    }





    private fun getLocalizacionActual(){

        // 1
        if (ActivityCompat.checkSelfPermission(context!!,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            // 2
            solicitarPermisoLocalizacion()
        } else {

            // 3
            // con esta sola linea, me dice mi ubicacion actual
            map?.isMyLocationEnabled = true
            map?.uiSettings?.isZoomControlsEnabled = true
            fusedLocationClient.lastLocation.addOnCompleteListener {

                val location = it.result
                if (location != null) {
                    // 4
                    val latLng = LatLng(location.latitude, location.longitude)

                    // 6
                    val update = CameraUpdateFactory.newLatLngZoom(latLng, 18.0f)
                    // 7
                    map?.moveCamera(update)
                } else {
                    // 8
                    Log.e(ContentValues.TAG, "No se encontro la localizacion")
                }
            }



        }


    }

    private fun solicitarPermisoLocalizacion(){
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            EnviarUbicacionFragment.SOLICITAR_LOCALIZACION
        )
    }

    private fun configurarLocalizacionCliente(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
    }


}
