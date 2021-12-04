package io.github.n0g4y0.deporapp.ui.equipo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.P_Equipo
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_seleccion_equipo.*
import kotlinx.android.synthetic.main.view_holder_p_equipo.view.*

class SeleccionEquipoFragment : Fragment(R.layout.fragment_seleccion_equipo) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { SeleccionEquipoAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        seleccion_equipo_recycler_view.layoutManager = LinearLayoutManager(context)
        seleccion_equipo_recycler_view.adapter = adaptador

        deporappViewModel?.getLista_p_equipos().observe(this, Observer { lista_P_equipos: List<P_Equipo> ->

            adaptador.actualizar(lista_P_equipos)
        })


        adaptador.setItemClickListener {p_equipo ->

            deporappViewModel.setidEquipoEncuentro(p_equipo)

            findNavController().popBackStack(R.id.crearJuegoFragment,false)

        }


    }

    /**
     * la clase ViewHolder
     * */


    private inner class SeleccionEquipoViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_p_equipo ,parent ,false)) {


        fun bind(p_equipo: P_Equipo){

            itemView.tv_nombre_p_equipo.text = p_equipo.nombre_equipo

        }

    }

    /**
     * la clase Adapter
     *
     * */

    private inner class SeleccionEquipoAdapter(): RecyclerView.Adapter<SeleccionEquipoViewHolder>() {

        private val lista_p_equipos: MutableList<P_Equipo> = mutableListOf()

        private var itemClickListener: ((P_Equipo) -> Unit)? = null



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeleccionEquipoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return SeleccionEquipoViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: SeleccionEquipoViewHolder, position: Int){

            val item = lista_p_equipos[position]

            holder.bind(item)

            // agregando comportamiento a los itemView:
            holder.itemView.setOnClickListener {
                itemClickListener?.invoke(item)
            }

        }

        fun setItemClickListener(listener: (p_equipo: P_Equipo) -> Unit){

            itemClickListener = listener

        }

        override fun getItemCount(): Int = lista_p_equipos.size


        fun actualizar(listaDeItems: List<P_Equipo>){
            lista_p_equipos.clear()
            lista_p_equipos.addAll(listaDeItems)
            notifyDataSetChanged()
        }




    }




}