package io.github.n0g4y0.deporapp.ui.encuentro


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.n0g4y0.deporapp.R
import kotlinx.android.synthetic.main.fragment_lista_pendientes.*

/**
 * A simple [Fragment] subclass.
 */
class ListaPendientesFragment : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? =  inflater.inflate(R.layout.fragment_lista_pendientes,container,false)

}
