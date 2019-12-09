package io.github.n0g4y0.deporapp.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.util.DateUtils
import io.github.n0g4y0.deporapp.viewholder.CanchaViewHolder

class CanchaAdapter(private val fechaUtils: DateUtils): RecyclerView.Adapter<CanchaViewHolder>() {

    private val listaCanchas: MutableList<Cancha> = mutableListOf()

    private var itemClickListener: ((Cancha) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanchaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CanchaViewHolder(inflater, parent,fechaUtils)
    }

    override fun onBindViewHolder(holder: CanchaViewHolder, position: Int){

        val item = listaCanchas[position]
        holder.bind(item)

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