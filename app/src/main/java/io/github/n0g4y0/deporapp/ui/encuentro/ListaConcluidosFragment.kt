package io.github.n0g4y0.deporapp.ui.encuentro


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Encuentro
import io.github.n0g4y0.deporapp.util.DateUtils
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_lista_concluidos.*
import kotlinx.android.synthetic.main.fragment_lista_juegos.*
import kotlinx.android.synthetic.main.view_holder_encuentro.view.*
import kotlinx.android.synthetic.main.view_holder_encuentro_concluido.view.*

/**
 *
 */
class ListaConcluidosFragment : Fragment(R.layout.fragment_lista_concluidos) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { ConcluidosAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        encuentros_concluidos_recycler_view.layoutManager = LinearLayoutManager(context)

        encuentros_concluidos_recycler_view.adapter = adaptador

        deporappViewModel?.getListaEncuentrosConcluidos().observe(this, Observer { listaEncuentros: List<Encuentro> ->

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
    private inner class ConcluidosAdapter: RecyclerView.Adapter<ListaConcluidosFragment.ConcluidosViewHolder>(){


        private val listaEncuentros: MutableList<Encuentro> = mutableListOf()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcluidosViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return ConcluidosViewHolder(inflater, parent)

        }

        override fun getItemCount() = listaEncuentros.size

        override fun onBindViewHolder(holder: ConcluidosViewHolder, position: Int) {

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
    private inner class ConcluidosViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_encuentro_concluido ,parent ,false)){

        var conversor = DateUtils()

        fun bind(encuentro: Encuentro){

            itemView.tv_nombre_encuentro_concluido.text = encuentro.nombre
            itemView.tv_dia_encuentro_concluido.text = conversor.convertirTimestampDia(encuentro.fecha)
            itemView.tv_nombre_mes_encuentro_concluido.text = conversor.convertirTimestampNombreMes(encuentro.fecha)
            itemView.tv_hora_encuentro_concluido.text = conversor.convertirTimeStampAHora(encuentro.hora)
            itemView.tv_cantidad_cupos_encuentro_concluido.text = "${encuentro.cupos} Cupos"
            itemView.tv_deporte_practicar_encuentro_concluido.text = encuentro.deporte
            itemView.tv_apodo_usuario_encuentro_concluido.text = encuentro.fk_usuario_nick

        }

    }


}
