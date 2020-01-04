package io.github.n0g4y0.deporapp.ui.comentario


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import io.github.n0g4y0.deporapp.R
import kotlinx.android.synthetic.main.fragment_comentario.*

/**
 * clase para manejar los comentarios
 */
class ComentarioFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comentario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
      //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
