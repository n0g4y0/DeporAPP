package io.github.n0g4y0.deporapp.ui.mapa

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_mapa.*

class MapaFragment : Fragment(R.layout.fragment_mapa),OnMapReadyCallback  {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private var map: GoogleMap? = null                  //  the overall Google Maps Object

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private var marcadores = HashMap<Long,Marker>()



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

        configurarMapListeners()

        configurarLocalizacionCliente()
        getLocalizacionActual()

        crearMarcadoresObservers()

    }


    private fun crearMarcadoresObservers() {

        deporappViewModel.getListaCanchas().observe(this, Observer { listaCanchas: List<Cancha> ->

            if (listaCanchas != null){


                map?.clear()
                marcadores.clear()

                listaCanchas?.let {

                    mostrarTodosLosMarcados(it)

                }



            }

        })



    }

    private fun mostrarTodosLosMarcados(marcados: List<Cancha>){

        for (marcado in marcados){
            agregarLugarMarcado(marcado)
        }


    }

    private fun agregarLugarMarcado(marcador: Cancha): Marker? {

        val punto = map?.addMarker(MarkerOptions()
            .position(LatLng(marcador.ubicacion_lat,marcador.ubicacion_long))
            .title(marcador.titulo)
            .alpha(0.8f)
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_locate_cancha_small))

        )

        return punto

    }

    private fun configurarMapListeners() {

        map?.setInfoWindowAdapter(PuntoInfoWindowAdapter(this))

        map?.setOnInfoWindowClickListener {

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

                    // directamente me muestra mi ubicacion actual.
                    val update = CameraUpdateFactory.newLatLngZoom(latLng, 14.0f)
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