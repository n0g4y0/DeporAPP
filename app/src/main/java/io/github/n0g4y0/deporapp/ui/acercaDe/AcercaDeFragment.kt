package io.github.n0g4y0.deporapp.ui.acercaDe


import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.n0g4y0.deporapp.R
import kotlinx.android.synthetic.main.fragment_acerca_de.*

/**
 * A simple [Fragment] subclass.
 */
class AcercaDeFragment : Fragment(R.layout.fragment_acerca_de) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text =
            "<h3>Deporapp</h3>\n" +
            "<p>Es una aplicacion que te permitira Realizar encuentros deportivos, con personas que comparten la misma pasion por tu deporte. </p>\n" +
            "<p>Esta APP se apoya en la tecnologia de la Geolocalizacion. </p> \n"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            textView.text = Html.fromHtml(text)
        }

    }


}
