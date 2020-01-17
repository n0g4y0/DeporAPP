package io.github.n0g4y0.deporapp.ui.cancha


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Encuentro
import io.github.n0g4y0.deporapp.util.DateUtils
import io.github.n0g4y0.deporapp.util.ImageBinding
import io.github.n0g4y0.deporapp.util.mostrarMensaje
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_detalle_cancha.*
import kotlinx.android.synthetic.main.view_holder_detalle_cancha.view.*
import kotlinx.android.synthetic.main.view_holder_encuentro.view.*

/**
 * detalle cancha, muestra los encuentros respectivos por cancha:
 */
class DetalleCanchaFragment : Fragment(R.layout.fragment_detalle_cancha) {

    private val adaptador by lazy { DetalleCanchaAdapter() }


    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private var argus: DetalleCanchaFragmentArgs? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            argus = DetalleCanchaFragmentArgs.fromBundle(it)

            nombre_Cancha_detalle.text = argus?.tituloCancha

            deporappViewModel.traerCancharDesdeFirestrore(argus?.idCancha!!)

            deporappViewModel.canchaConsultada.observe(this, Observer { canchaConsultada ->

                val foto = canchaConsultada.foto_url
                ImageBinding.setImageUrl(imagen_url_cancha_detalle,canchaConsultada.foto_url)

            })

        }



        recycler_view_detalle_cancha.layoutManager = LinearLayoutManager(context)

        recycler_view_detalle_cancha.adapter = adaptador


        deporappViewModel.getListaEncuentrosByIDCancha(argus?.idCancha!!).observe(this, Observer { listaEncuentros: List<Encuentro> ->

            adaptador.actualizar(listaEncuentros)
        })

    }


    /**
     * creamos las clases para el recyclerView, seran CLASES INTERNAS:
     *
     * */

    /*
       * primero creamos el adaptador:
       *
       * */
    private inner class DetalleCanchaAdapter: RecyclerView.Adapter<DetalleCanchaViewHolder>(){


        private val listaEncuentros: MutableList<Encuentro> = mutableListOf()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleCanchaViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return DetalleCanchaViewHolder(inflater, parent)

        }

        override fun getItemCount() = listaEncuentros.size

        override fun onBindViewHolder(holder: DetalleCanchaViewHolder, position: Int) {

            val item = listaEncuentros[position]
            holder.bind(item)

        }


        fun actualizar(listaDeItems: List<Encuentro>){
            listaEncuentros.clear()
            listaEncuentros.addAll(listaDeItems)
            notifyDataSetChanged()
        }


    }




    /*
* creamos el ViewHolder
*
* */
    private inner class DetalleCanchaViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_detalle_cancha,parent ,false)) {


        var conversor = DateUtils()

        fun bind(encuentro: Encuentro) {

            itemView.tv_nombre_encuentro_detalle_cancha.text = encuentro.nombre
            itemView.tv_dia_encuentro_detalle_cancha.text = conversor.convertirTimestampDia(encuentro.fecha)
            itemView.tv_nombre_mes_encuentro_detalle_cancha.text =
                conversor.convertirTimestampNombreMes(encuentro.fecha)
            itemView.tv_cantidad_cupos_encuentro_detalle_cancha.text = "${encuentro.cupos} Cupos"
            itemView.tv_hora_encuentro_detalle_cancha.text = conversor.convertirTimeStampAHora(encuentro.hora)
            itemView.tv_deporte_practicar_encuentro_detalle_cancha.text = encuentro.deporte
            itemView.tv_nota_adicional_encuentro_detalle_cancha.text = encuentro.nota
            itemView.tv_apodo_usuario_encuentro_detalle_cancha.text = encuentro.fk_usuario_nick
            ImageBinding.setImageUrl(
                itemView.iv_perfil_foto_encuentro_detalle_cancha,
                encuentro.fk_usuario_foto_url
            )

        }

    }

}
