package io.github.n0g4y0.deporapp.ui.encuentro


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_lista_juegos.*

/**
 *
 */
class ListaJuegosFragment : Fragment(R.layout.fragment_lista_juegos) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

   // private val adaptador by lazy { EncuentroAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)

        encuentros_recycler_view.layoutManager = LinearLayoutManager(context)
      //  encuentros_recycler_view.adapter = adaptador

      //  deporappViewModel?.getListaEquipos().observe(this, Observer { listaEquipos: List<Encuentro> ->

       //     adaptador.actualizar(listaEncuentros)
       // })


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


}
