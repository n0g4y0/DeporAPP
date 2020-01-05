package io.github.n0g4y0.deporapp.ui.comentario


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_comentario.*

/**
 * clase para manejar los comentarios
 */
class ComentarioFragment : DialogFragment() {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)


    private var argSeguros: ComentarioFragmentArgs? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comentario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            argSeguros = ComentarioFragmentArgs.fromBundle(it)
        }


        dialog?.setOnShowListener {
            dialog?.setTitle("Califica el Encuentro")
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        btn_enviar_actual_comentario.setOnClickListener {

            enviarDatosFirebase()
            dismiss()

        }
    }

    private fun enviarDatosFirebase() {

        val puntuacion = puntuacion_al_encuentro.rating
        val descripcion = descripcion_comentario.text.toString().trim()
        val idEncuentro = argSeguros?.idEncuentroActual
        val idUsuario = deporappViewModel.getIdUsuarioActual()
        val apodoUsuario = deporappViewModel.getApodoUsuarioActual()


        deporappViewModel.crearComentarioEnFirestoreConHilos(puntuacion,descripcion,idEncuentro!!,idUsuario!!,apodoUsuario!!)

        // las siguientes lineas, evitan que un determinado usuario, vuelva a comentar:
        deporappViewModel.elUsuarioCalificoEncuentro(idEncuentro,idUsuario)
        deporappViewModel.comentoElUsuario.observe(this, Observer { respuesta ->


            if (!respuesta) {
                btn_enviar_actual_comentario.visibility = View.GONE
            }
        })

    }


}
