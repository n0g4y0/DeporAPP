package io.github.n0g4y0.deporapp.ui.equipo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.P_Equipo
import io.github.n0g4y0.deporapp.util.ImageBinding
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_lista_integrantes.*
import kotlinx.android.synthetic.main.view_holder_usuario.view.*

class ListaIntegrantesEquipoFragment : Fragment(R.layout.fragment_lista_integrantes) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { ListaIntegrantesEquipoAdapter() }

    private var argus: ListaIntegrantesEquipoFragmentArgs? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            argus = ListaIntegrantesEquipoFragmentArgs.fromBundle(it)

        }


        setHasOptionsMenu(true)

        recyclerView_lista_integrantes_equipo.layoutManager = LinearLayoutManager(context)
        recyclerView_lista_integrantes_equipo.adapter = adaptador


        deporappViewModel.getLista_P_equipoById(argus?.idEquipo!!)
            .observe(this, Observer { lista_P_equipo: List<P_Equipo> ->

                adaptador.actualizar(lista_P_equipo)
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
    private inner class ListaIntegrantesEquipoAdapter :
        RecyclerView.Adapter<ListaIntegrantesEquipoViewHolder>() {

        private val lista_P_equipo: MutableList<P_Equipo> = mutableListOf()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ListaIntegrantesEquipoViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return ListaIntegrantesEquipoViewHolder(inflater, parent)

        }

        override fun getItemCount() = lista_P_equipo.size

        override fun onBindViewHolder(holder: ListaIntegrantesEquipoViewHolder, position: Int) {

            val item = lista_P_equipo[position]
            holder.bind(item)

        }

        fun actualizar(listaDeItems: List<P_Equipo>) {
            lista_P_equipo.clear()
            lista_P_equipo.addAll(listaDeItems)
            notifyDataSetChanged()
        }

    }

    /*
    * creamos el ViewHolder
    *
    * */

    private inner class ListaIntegrantesEquipoViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_usuario, parent, false)) {


        fun bind(p_equipo: P_Equipo) {

            itemView.tv_nombre_usuario.text = p_equipo.nombre_usuario
            ImageBinding.setImageUrl(itemView.iv_perfil_usuario_participante, p_equipo.photo_usuario)

        }

    }



}