package io.github.n0g4y0.deporapp.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Anuncio
import io.github.n0g4y0.deporapp.viewholder.AnuncioViewHolder

class AnuncioAdapter: RecyclerView.Adapter<AnuncioViewHolder>() {

    private val listaAnuncios: MutableList<Anuncio> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnuncioViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return AnuncioViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: AnuncioViewHolder, position: Int){

        val item = listaAnuncios[position]
        holder.bind(item)

    }

    override fun getItemCount() = listaAnuncios.size


    fun actualizar(listaDeItems: List<Anuncio>){
        listaAnuncios.clear()
        listaAnuncios.addAll(listaDeItems)
        notifyDataSetChanged()
    }






}