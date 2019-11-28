package io.github.n0g4y0.deporapp.ui.cancha


import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.adaptador.CanchaAdapter
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_lista_canchas.*

private const val TAG = "ListaCanchaFragment"

class ListaCanchasFragment : Fragment(R.layout.fragment_lista_canchas) {

    private val deporappViewModel:DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { CanchaAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        adaptador.setItemClickListener {

        }


        canchas_recycler_view.layoutManager = LinearLayoutManager(context)
        canchas_recycler_view.adapter = adaptador




    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.crear_cancha, menu)
    }

}
