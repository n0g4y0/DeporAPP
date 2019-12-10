package io.github.n0g4y0.deporapp.ui.anuncio


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.adaptador.AnuncioAdapter
import io.github.n0g4y0.deporapp.model.Anuncio
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_anuncio.*

/**
 * clase diseñada para los anuncios de la app
 */
class AnuncioFragment : Fragment(R.layout.fragment_anuncio) {

        private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

        private val adaptador by lazy { AnuncioAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        anuncios_recycler_view.layoutManager = LinearLayoutManager(context)
        anuncios_recycler_view.adapter = adaptador

        //estar a la ESCUCHA de los nuevos anuncios:
        deporappViewModel?.getListaAnuncios().observe(this, Observer { listaAnuncios: List<Anuncio> ->

            adaptador.actualizar(listaAnuncios)
        })
    }






}
