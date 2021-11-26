package io.github.n0g4y0.deporapp.ui.equipo


import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Equipo
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_lista_equipos.*
import kotlinx.android.synthetic.main.view_holder_equipo.view.*


class ListaEquiposFragment : Fragment(R.layout.fragment_lista_equipos) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { EquipoAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        equipos_recycler_view.layoutManager = LinearLayoutManager(context)
        equipos_recycler_view.adapter = adaptador

        deporappViewModel?.getListaEquipos().observe(this, Observer { listaEquipos: List<Equipo> ->

            adaptador.actualizar(listaEquipos)
        })



    }

    // funcion que muestra el menu, el icono de MAS CANCHA:

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.crear_equipo,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.accion_crear_equipo -> findNavController().navigate(R.id.crearEquipoFragment)

        }

        return super.onOptionsItemSelected(item)
    }



/*
* creamos el ViewHolder
*
* */
    private inner class EquipoViewHolder(inflater: LayoutInflater, parent: ViewGroup)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_equipo ,parent ,false)){


    fun bind(equipo: Equipo){

        itemView.tv_nombre_equipo.text = equipo.nombre

        itemView.btn_unirse_equipo.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setMessage("ATENCION!! \n\nEl envio de una Solicitud Implica el compromiso de cumplir con los encuentros deportivos del equipo caso de que lo acepten.\n" +
                        "desea continuar?")
                .setPositiveButton("SI"){ _,_ ->


                }
                .setNegativeButton("NO",null)
                .create().show()

        }

    }

}



/*
* primero creamos el adaptador:
*
* */
    private inner class EquipoAdapter: RecyclerView.Adapter<ListaEquiposFragment.EquipoViewHolder>(){


    private val listaEquipos: MutableList<Equipo> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return EquipoViewHolder(inflater, parent)

    }

    override fun getItemCount() = listaEquipos.size

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {

        val item = listaEquipos[position]
        holder.bind(item)

    }


    fun actualizar(listaDeItems: List<Equipo>){
        listaEquipos.clear()
        listaEquipos.addAll(listaDeItems)
        notifyDataSetChanged()
    }


}


}

/**
 * creamos las clases para el recyclerView, seran CLASES INTERNAS:
 *
 * */



