package io.github.n0g4y0.deporapp.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.viewholder.CanchaViewHolder
import kotlinx.android.synthetic.main.view_holder_cancha.view.*

class CanchaAdapter(): RecyclerView.Adapter<CanchaViewHolder>() {

    private val lista: MutableList<Cancha> = mutableListOf()

    private var itemClickListener: ((Cancha) -> Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanchaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CanchaViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: CanchaViewHolder, position: Int) {

        val item = lista[position]


        holder.itemView.setOnClickListener {

            itemClickListener?.invoke(item)

        }

        holder.bind(item)



    }

    fun setItemClickListener(listener: (cancha: Cancha) -> Unit){

        itemClickListener = listener

    }

    override fun getItemCount(): Int = lista.size


    fun actualizar(listaDeItems: List<Cancha>){
        lista.clear()
        lista.addAll(listaDeItems)
        notifyDataSetChanged()
    }




}