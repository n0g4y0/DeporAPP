package io.github.n0g4y0.deporapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Cancha
import java.util.*

private const val TAG = "ListaCanchaFragment"

class ListaCanchasFragment : Fragment() {

    /*
   * Interfaz necesaria, para la ACTIVIDAD HOSTING
   * */
    interface Callbacks {
        fun enCanchaSeleccionada(canchaId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var canchaRecyclerView : RecyclerView

    private var adapter : CanchaAdapter? = CanchaAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_canchas,container,false)

        canchaRecyclerView = view.findViewById(R.id.cancha_recycler_view) as RecyclerView
        canchaRecyclerView.layoutManager = LinearLayoutManager(context)
        canchaRecyclerView.adapter = adapter

        return view
    }






    /*
* to-do el codigo que signifique vincular dentro del proyecto, deberia ser implementado en esta clase HOLDER
* */

    private inner class CanchaHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var cancha: Cancha

        val tituloTextView: TextView = itemView.findViewById(R.id.cancha_titulo)

        init {
            itemView.setOnClickListener(this)
        }

        // funcion que vincula los datos del CRIME, con los WIDGETs

        fun bind(cancha : Cancha){

            this.cancha = cancha

            // asignando el texto, al atributo TEXT de los WIDGETs
            tituloTextView.text = this.cancha.titulo

        }

        override fun onClick(v: View?) {
            callbacks?.enCanchaSeleccionada(cancha.id)
        }


    }

    /*
    * el adapter es un objeto controlador, que se situa entre el RecyclerView, y el conjunto de datos
    * que desea mostrar del recyclerView.
    * */
    private inner class CanchaAdapter(var canchas: List<Cancha>)
        : RecyclerView.Adapter<CanchaHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : CanchaHolder {
            val view = layoutInflater.inflate(R.layout.lista_item_cancha,parent,false)
            return CanchaHolder(view)

        }

        override fun getItemCount() = canchas.size


        override fun onBindViewHolder(holder: CanchaHolder, position: Int) {

            val cancha = canchas[position]
            holder.bind(cancha)

        }


    }


}