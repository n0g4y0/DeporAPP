package io.github.n0g4y0.deporapp.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Anuncio
import io.github.n0g4y0.deporapp.util.DateTimeHelper
import kotlinx.android.synthetic.main.view_holder_anuncio.view.*



class AnuncioViewHolder (inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_anuncio ,parent ,false)) {

    private val conversorFecha =  DateTimeHelper()


    fun bind(anuncio: Anuncio){

        itemView.tv_titulo_anuncio.text = anuncio.titulo
        itemView.tv_descripcion.text = anuncio.descripcion
        itemView.tv_fecha.text = conversorFecha.parse(anuncio.fecha)

    }


}