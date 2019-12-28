package io.github.n0g4y0.deporapp.ui.cancha


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
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.util.DateTimeHelper
import io.github.n0g4y0.deporapp.util.DateUtils
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_seleccion_cancha.*
import kotlinx.android.synthetic.main.view_holder_cancha.view.*

/**
 * Clase para seleccionar una cancha, una ayuda para crear el encuentro.
 *
 */
class SeleccionCanchaFragment : Fragment(R.layout.fragment_seleccion_cancha) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { SeleccionCanchaAdapter(DateUtils()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        seleccion_cancha_recycler_view.layoutManager = LinearLayoutManager(context)
        seleccion_cancha_recycler_view.adapter = adaptador

        deporappViewModel?.getListaCanchas().observe(this, Observer { listaCanchas: List<Cancha> ->

            adaptador.actualizar(listaCanchas)
        })


        adaptador.setItemClickListener {cancha ->
            findNavController().popBackStack(R.id.crearJuegoFragment,false)
            Log.d("prueba","prueba de click")
        }


    }


/**
 * la clase ViewHolder
 * */

private inner class SeleccionCanchaViewHolder(inflater: LayoutInflater, parent: ViewGroup, private val dateUtils: DateUtils) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_cancha ,parent ,false)) {

        private val datetimeHelper = DateTimeHelper()

        private lateinit var cancha: Cancha


        fun bind(cancha: Cancha){

            itemView.tv_titulo.text = cancha.titulo
            itemView.tv_fecha_equipo.text = dateUtils.mapToNormalisedDateText(cancha.fecha_timestamp)

        }

}




/**
 * la clase Adapter
 *
 * */


private inner class SeleccionCanchaAdapter(private val fechaUtils: DateUtils): RecyclerView.Adapter<SeleccionCanchaViewHolder>() {

    private val listaCanchas: MutableList<Cancha> = mutableListOf()

    private var itemClickListener: ((Cancha) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeleccionCanchaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SeleccionCanchaViewHolder(inflater, parent,fechaUtils)
    }

    override fun onBindViewHolder(holder: SeleccionCanchaViewHolder, position: Int){

        val item = listaCanchas[position]

        holder.bind(item)

        // agregando comportamiento a los itemView:
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(item)
        }

    }

    fun setItemClickListener(listener: (cancha: Cancha) -> Unit){

        itemClickListener = listener

    }

    override fun getItemCount(): Int = listaCanchas.size


    fun actualizar(listaDeItems: List<Cancha>){
        listaCanchas.clear()
        listaCanchas.addAll(listaDeItems)
        notifyDataSetChanged()
    }




}



}
