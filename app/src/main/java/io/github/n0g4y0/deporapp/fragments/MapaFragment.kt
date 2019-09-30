package io.github.n0g4y0.deporapp.fragments

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

class MapaFragment : Fragment(),OnMapReadyCallback  {

    private var mMap: GoogleMap? = null                  //  the overall Google Maps Object
    private var myLocation: LatLng? = null              // my current GPS location
    private var prevCLick: Marker? = null                //  last marker I clicked On
    private var mMapView : MapView? = null
    private var mView : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMapView = mView?.findViewById(R.id.mapa_view) as MapView
        if (mMapView != null ){
            mMapView!!.onCreate(null)
            mMapView!!.onResume()
            mMapView!!.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        MapsInitializer.initialize(context)

        mMap = googleMap
        // añadiendo una marca en bolivia:

        val bolivia = LatLng(-16.2362083,-68.0469867)
        mMap.let {
            it?.addMarker(MarkerOptions().position(bolivia).title("Marca en Bolivia"))
            it?.moveCamera(CameraUpdateFactory.newLatLng(bolivia))
        }


    }


}