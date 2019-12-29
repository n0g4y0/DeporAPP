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
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_lista_juegos.*
import kotlinx.android.synthetic.main.view_holder_encuentro.view.*

/**
 *
 */
class ListaJuegosFragment : Fragment(R.layout.fragment_lista_juegos) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { EncuentroAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)

        encuentros_recycler_view.layoutManager = LinearLayoutManager(context)

        encuentros_recycler_view.adapter = adaptador

        deporappViewModel?.getListaEncuentros().observe(this, Observer { listaEncuentros: List<Encuentro> ->

            adaptador.actualizar(listaEncuentros)
        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.crear_encuentro,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.accion_crear_encuentro -> findNavController().navigate(R.id.crearJuegoFragment)

        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * creamos las clases para el recyclerView, seran CLASES INTERNAS:
     *
     * */




    /*
    * primero creamos el adaptador:
    *
    * */
    private inner class EncuentroAdapter: RecyclerView.Adapter<ListaJuegosFragment.EncuentroViewHolder>(){


        private val listaEncuentros: MutableList<Encuentro> = mutableListOf()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncuentroViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return EncuentroViewHolder(inflater, parent)

        }

        override fun getItemCount() = listaEncuentros.size

        override fun onBindViewHolder(holder: EncuentroViewHolder, position: Int) {

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
    private inner class EncuentroViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_encuentro ,parent ,false)){


        fun bind(encuentro: Encuentro){

            itemView.tv_nombre_encuentro.text = encuentro.nombre

        }

    }


}
