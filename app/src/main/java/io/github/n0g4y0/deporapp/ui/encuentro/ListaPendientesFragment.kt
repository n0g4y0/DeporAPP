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

    companion object {
        private const val ARG_OBJECT = "object"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_pendientes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
           textView2.text = "FRAGMENT0 " + getInt(ARG_OBJECT).toString()
        }
    }


}
