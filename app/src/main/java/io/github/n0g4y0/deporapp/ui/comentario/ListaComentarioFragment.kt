package io.github.n0g4y0.deporapp.ui.comentario


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Comentario
import io.github.n0g4y0.deporapp.util.DateTimeHelper
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_lista_comentario.*
import kotlinx.android.synthetic.main.view_holder_comentario.view.*

/**
 *
 * Fragment que se utilizara para mostrar los comentarios y calificaciones al encuentro deportivo
 * contiene un recyclerview y los datos del encuentro deportivo.
 *
 */
class ListaComentarioFragment : Fragment(R.layout.fragment_lista_comentario) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { ListaComentariosAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_comentar_lista_comentario.setOnClickListener {
            findNavController().navigate(R.id.comentarioFragment)
        }

        recyclerView_lista_comentario.layoutManager = LinearLayoutManager(context)

        recyclerView_lista_comentario.adapter = adaptador

        val idEncuentro: String = ""

        deporappViewModel?.getListaComentariosEncuentro(idEncuentro).observe(this, Observer { listaComentarios: List<Comentario> ->

            adaptador.actualizar(listaComentarios)
        })


    }



    /**
     * creamos las clases para el recyclerView, seran CLASES INTERNAS:
     *
     * */


    /*
       * primero creamos el adaptador:
       *
       * */
    private inner class ListaComentariosAdapter: RecyclerView.Adapter<ListaComentarioFragment.ListaComentariosViewHolder>(){


        private val listaComentarios: MutableList<Comentario> = mutableListOf()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaComentariosViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return ListaComentariosViewHolder(inflater, parent)

        }

        override fun getItemCount() = listaComentarios.size

        override fun onBindViewHolder(holder: ListaComentariosViewHolder, position: Int) {

            val item = listaComentarios[position]
            holder.bind(item)

        }


        fun actualizar(listaDeItems: List<Comentario>){
            listaComentarios.clear()
            listaComentarios.addAll(listaDeItems)
            notifyDataSetChanged()
        }


    }




    /*
* creamos el ViewHolder
*
* */
    private inner class ListaComentariosViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_comentario,parent ,false)){


        var conversor = DateTimeHelper()

        fun bind(comentario: Comentario){


            itemView.tv_puntuacion_comentario.rating = comentario.puntuacion
            itemView.tv_fecha_publicacion_comentario.text = conversor.parse(comentario.fecha)
            itemView.tv_descripcion_comentario.text = comentario.descripcion


        }

    }






}
