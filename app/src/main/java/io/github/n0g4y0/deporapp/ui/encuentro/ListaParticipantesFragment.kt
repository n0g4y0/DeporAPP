package io.github.n0g4y0.deporapp.ui.encuentro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.P_Encuentro
import io.github.n0g4y0.deporapp.util.ImageBinding
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_lista_participantes.*
import kotlinx.android.synthetic.main.view_holder_usuario.view.*

class ListaParticipantesFragment : Fragment(R.layout.fragment_lista_participantes) {


    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { ListaParticipantesAdapter() }

    private var argus: ListaParticipantesFragmentArgs? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            argus = ListaParticipantesFragmentArgs.fromBundle(it)
            Log.d("id del equipo es: ", "id equipo 1:  ${argus?.idEquipo!!}")
            deporappViewModel.traerEquipoDesdeFirestore(argus?.idEquipo!!)

            deporappViewModel.equipoConsultado.observe(this, Observer { respuesta ->

                if (respuesta != null) {
                    tv_nombre_equipo_anfitrion.text = respuesta.nombre
                }
            })


        }

        setHasOptionsMenu(true)

        recyclerView_lista_participantes.layoutManager = LinearLayoutManager(context)
        recyclerView_lista_participantes.adapter = adaptador

        deporappViewModel.getLista_P_encuentroById(argus?.id!!)
            .observe(this, Observer { lista_P_encuentro: List<P_Encuentro> ->

                adaptador.actualizar(lista_P_encuentro)
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
    private inner class ListaParticipantesAdapter: RecyclerView.Adapter<Lista_P_EncuentroViewHolder>(){

        private val lista_P_encuentro: MutableList<P_Encuentro> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Lista_P_EncuentroViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return Lista_P_EncuentroViewHolder(inflater, parent)

        }

        override fun getItemCount() = lista_P_encuentro.size

        override fun onBindViewHolder(holder: Lista_P_EncuentroViewHolder, position: Int) {

            val item = lista_P_encuentro[position]
            holder.bind(item)

        }

        fun actualizar(listaDeItems: List<P_Encuentro>){
            lista_P_encuentro.clear()
            lista_P_encuentro.addAll(listaDeItems)
            notifyDataSetChanged()
        }

    }

        /*
        * creamos el ViewHolder
        *
        * */

    private inner class Lista_P_EncuentroViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_usuario,parent ,false)){


        fun bind(p_encuentro: P_Encuentro){

            itemView.iv_perfil_usuario_participante
            itemView.tv_nombre_usuario.text = p_encuentro.nombre_usuario
            ImageBinding.setImageUrl(itemView.iv_perfil_usuario_participante,p_encuentro.photo_url)

        }

    }


}