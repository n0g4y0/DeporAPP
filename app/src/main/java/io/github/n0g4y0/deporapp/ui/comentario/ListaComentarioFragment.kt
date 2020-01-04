package io.github.n0g4y0.deporapp.ui.comentario


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import io.github.n0g4y0.deporapp.R
import kotlinx.android.synthetic.main.fragment_comentario.*
import kotlinx.android.synthetic.main.fragment_lista_comentario.*

/**
 *
 * Fragment que se utilizara para mostrar los comentarios y calificaciones al encuentro deportivo
 * contiene un recyclerview y los datos del encuentro deportivo.
 *
 */
class ListaComentarioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_comentario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_comentar_lista_comentario.setOnClickListener {
            findNavController().navigate(R.id.comentarioFragment)
        }
    }


}
