package io.github.n0g4y0.deporapp.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.R

class AnuncioViewHolder (itemView:View): RecyclerView.ViewHolder(itemView) {

    val anuncioTitulo = itemView.findViewById(R.id.tv_titulo) as TextView

    val anuncioDescripcion = itemView.findViewById(R.id.tv_descripcion) as TextView
}