package io.github.n0g4y0.deporapp.ui.cancha


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.adaptador.CanchaAdapter
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.util.DateUtils
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_lista_canchas.*

private const val TAG = "ListaCanchaFragment"

class ListaCanchasFragment : Fragment(R.layout.fragment_lista_canchas) {

    private val deporappViewModel:DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { CanchaAdapter(DateUtils()) }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        adaptador.setItemClickListener {

        }

        canchas_recycler_view.layoutManager = LinearLayoutManager(context)
        canchas_recycler_view.adapter = adaptador


        deporappViewModel?.getListaCanchas().observe(this, Observer { listaCanchas: List<Cancha> ->

            if (listaCanchas == null){
                Log.d("prueba","esta vacio")
            }else{
                Log.d("prueba","NO esta vacio")
            }

            adaptador.actualizar(listaCanchas)
        })

    }


    /*
    * funciones para manipular la creacion de canchas:
    * */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.crear_cancha, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.accion_crear_cancha -> findNavController().navigate(R.id.crearCanchaFragment)

        }

        return super.onOptionsItemSelected(item)
    }

}
