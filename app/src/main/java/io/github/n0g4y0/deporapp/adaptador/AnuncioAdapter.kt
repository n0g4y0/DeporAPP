package io.github.n0g4y0.deporapp.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Anuncio
import io.github.n0g4y0.deporapp.viewholder.AnuncioViewHolder

class AnuncioAdapter: RecyclerView.Adapter<AnuncioViewHolder>() {

    val listaAnuncios = arrayListOf("pruebas de carga", "prueba de listeners","subida de imagenes out", "sin concluir Fragments")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnuncioViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_anuncio,parent,false)

        return AnuncioViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaAnuncios.size
    }

    override fun onBindViewHolder(holder: AnuncioViewHolder, position: Int) {

        holder.anuncioTitulo.text = (position + 1).toString()

        holder.anuncioDescripcion.text = listaAnuncios[position]

    }



}