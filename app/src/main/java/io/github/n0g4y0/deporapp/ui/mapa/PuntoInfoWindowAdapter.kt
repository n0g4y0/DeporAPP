package io.github.n0g4y0.deporapp.ui.mapa

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import io.github.n0g4y0.deporapp.R

class PuntoInfoWindowAdapter(val contexto: Fragment): GoogleMap.InfoWindowAdapter {

    private val contents: View

    init {
        contents = contexto.layoutInflater.inflate(R.layout.info_window_punto,null)
    }

    // por ahora no utilizaremos esta funcion
    override fun getInfoWindow(marker: Marker?): View? {

        return null
    }


    override fun getInfoContents(marker: Marker?): View? {

        val tituloView = contents.findViewById<TextView>(R.id.titulo_punto_mapa)
        tituloView.text = marker?.title ?: ""

        return contents
    }


}