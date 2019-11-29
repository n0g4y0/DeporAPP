package io.github.n0g4y0.deporapp.ui.mapa

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.github.n0g4y0.deporapp.R
import kotlinx.android.synthetic.main.fragment_mapa.*

class MapaFragment : Fragment(R.layout.fragment_mapa),OnMapReadyCallback  {

    private var map: GoogleMap? = null                  //  the overall Google Maps Object

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    companion object {
        private const val SOLICITAR_LOCALIZACION = 1

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapa_view.onCreate(savedInstanceState)
        mapa_view.onResume()
        mapa_view.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap?) {

        map = googleMap
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
                    Log.e(TAG, "No se encontro la localizacion")
                }
            }



        }


    }


    private fun solicitarPermisoLocalizacion(){
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            SOLICITAR_LOCALIZACION
        )
    }

    private fun configurarLocalizacionCliente(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
    }


}