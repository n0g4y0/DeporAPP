package io.github.n0g4y0.deporapp.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.util.DateTimeHelper
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.view_holder_cancha.view.*

class CanchaViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_cancha ,parent ,false)) {

    private val datetimeHelper = DateTimeHelper()


    fun bind(cancha: Cancha){

        itemView.tv_name.text = cancha.titulo


    }

}