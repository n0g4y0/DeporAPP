package io.github.n0g4y0.deporapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.github.n0g4y0.deporapp.R
import kotlinx.android.synthetic.main.fragment_mapa.*

class MapaFragment : Fragment(),OnMapReadyCallback  {

    private var mMap: GoogleMap? = null                  //  the overall Google Maps Object
    private var myLocation: LatLng? = null              // my current GPS location
    private var prevCLick: Marker? = null                //  last marker I clicked On
    private var mMapView : MapView? = null
    private var mView : View? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapa_view.onCreate(savedInstanceState)
        mapa_view.onResume()
        mapa_view.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view = inflater.inflate(R.layout.fragment_mapa,container,false)
        mView = view

        return view
    }
    override fun onMapReady(googleMap: GoogleMap?) {

        mMap = googleMap
        // añadiendo una marca en bolivia:

        val cbba = LatLng(-17.3940469,-66.2339162)
        mMap.let {
            it?.addMarker(MarkerOptions().position(cbba).title("Marca en Bolivia"))
            it?.moveCamera(CameraUpdateFactory.newLatLng(cbba))


        }


    }


}