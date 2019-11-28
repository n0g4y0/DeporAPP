package io.github.n0g4y0.deporapp.ui.cancha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.adaptador.CanchaAdapter
import io.github.n0g4y0.deporapp.model.Cancha
import java.util.*

private const val TAG = "ListaCanchaFragment"

class ListaCanchasFragment : Fragment(R.layout.fragment_lista_canchas) {

    private val adaptador by lazy { CanchaAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adaptador.setItemClickListener {

        }




    }

}