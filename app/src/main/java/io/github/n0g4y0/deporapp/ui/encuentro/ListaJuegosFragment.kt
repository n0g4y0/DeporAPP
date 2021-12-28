package io.github.n0g4y0.deporapp.ui.encuentro


import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Encuentro
import io.github.n0g4y0.deporapp.util.DateUtils
import io.github.n0g4y0.deporapp.util.ImageBinding
import io.github.n0g4y0.deporapp.util.ImageUtils
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_lista_juegos.*
import kotlinx.android.synthetic.main.view_holder_encuentro.view.*

/**
 *
 */
class ListaJuegosFragment : Fragment(R.layout.fragment_lista_juegos) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    private val adaptador by lazy { EncuentroAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)

        encuentros_recycler_view.layoutManager = LinearLayoutManager(context)

        encuentros_recycler_view.adapter = adaptador

        deporappViewModel.getListaEncuentros()
            .observe(this, Observer { listaEncuentros: List<Encuentro> ->

                adaptador.actualizar(listaEncuentros)
            })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.crear_encuentro, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.accion_crear_encuentro -> findNavController().navigate(R.id.crearJuegoFragment)

        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * creamos las clases para el recyclerView, seran CLASES INTERNAS:
     *
     * */


    /*
    * primero creamos el adaptador:
    *
    * */
    private inner class EncuentroAdapter() :
        RecyclerView.Adapter<ListaJuegosFragment.EncuentroViewHolder>() {


        private val listaEncuentros: MutableList<Encuentro> = mutableListOf()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncuentroViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return EncuentroViewHolder(inflater, parent)

        }

        override fun getItemCount() = listaEncuentros.size

        override fun onBindViewHolder(holder: EncuentroViewHolder, position: Int) {

            val item = listaEncuentros[position]
            holder.bind(item)

        }


        fun actualizar(listaDeItems: List<Encuentro>) {
            listaEncuentros.clear()
            listaEncuentros.addAll(listaDeItems)
            notifyDataSetChanged()
        }


    }


    /*
* creamos el ViewHolder
*
* */
    private inner class EncuentroViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.view_holder_encuentro, parent, false)) {


        var conversor = DateUtils()


        fun bind(encuentro: Encuentro) {

            itemView.tv_nombre_encuentro.text = encuentro.nombre
            itemView.tv_dia_encuentro.text = conversor.convertirTimestampDia(encuentro.fecha)
            itemView.tv_nombre_mes_encuentro.text =
                conversor.convertirTimestampNombreMes(encuentro.fecha)

            if (encuentro.cupos <= 0){
                itemView.tv_cantidad_cupos_encuentro.visibility = View.GONE
            }
            itemView.tv_cantidad_cupos_encuentro.text = "${encuentro.cupos} Cupos"
            itemView.tv_hora_encuentro.text = conversor.convertirTimeStampAHora(encuentro.hora)
            itemView.tv_deporte_practicar_encuentro.text = encuentro.deporte
            itemView.tv_nota_adicional_encuentro.text = encuentro.nota
            itemView.tv_apodo_usuario_encuentro.text = encuentro.fk_usuario_nick
            ImageBinding.setImageUrl(
                itemView.iv_perfil_foto_encuentro,
                encuentro.fk_usuario_foto_url
            )

            itemView.abrir_localizacion_encuentro.setOnClickListener {

                val codigoUbicacion = "geo:${encuentro.fk_cancha_lat},${encuentro.fk_cancha_lng}?" +
                        "q=${encuentro.fk_cancha_lat},${encuentro.fk_cancha_lng}(Cancha)"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(codigoUbicacion))
                startActivity(intent)

            }



            itemView.btn_comentar_encuentro.setOnClickListener {

                val bundle = Bundle()
                bundle.putString("id_encuentro", encuentro.id)
                bundle.putString("nombre", encuentro.nombre)
                bundle.putLong("fecha", encuentro.fecha)
                bundle.putLong("hora", encuentro.hora)
                bundle.putString("apodo", encuentro.fk_usuario_nick)
                bundle.putString("id_usuario", encuentro.id_creador)

                findNavController().navigate(R.id.action_encuentro_to_comentarios, bundle)
            }

            itemView.btn_postular_encuentro.setOnClickListener {

                AlertDialog.Builder(requireContext())
                    .setMessage(
                        "ATENCION!! \n\nEl envio de una Solicitud Implica el compromiso de participar en caso de que lo acepten.\n" +
                                "desea continuar?"
                    )
                    .setPositiveButton("SI") { _, _ ->

                        deporappViewModel.traerAlUsuarioDesdeFirestore(deporappViewModel.getIdUsuarioActual())

                        deporappViewModel.usuarioConsultado.observe(
                            this@ListaJuegosFragment,
                            Observer { respuesta ->

                                if (respuesta != null) {

                                    enviarP_EncuentroAlFirebase(
                                        encuentro.id,
                                        respuesta.nombre,
                                        deporappViewModel.getPhotoUrlUsuarioActual(),
                                        deporappViewModel.getIdUsuarioActual()
                                    )

                                }

                            })
                        Toast.makeText(activity?.applicationContext, "Enviando Solicitud....", Toast.LENGTH_LONG).show()

                    }
                    .setNegativeButton("NO", null)
                    .create().show()


                deporappViewModel.disminuirCantParticipantes(encuentro)
            }


            itemView.tv_cardview_encuentro.setOnClickListener {
                //Toast.makeText(activity?.applicationContext, "show something...", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("id", encuentro.id)
                bundle.putString("id_equipo", encuentro.id_equipo)

                findNavController().navigate(R.id.action_encuentro_to_listaParticipantes, bundle)
            }


        }

    }

    private fun enviarP_EncuentroAlFirebase(
        idEncuentro: String,
        nombreUsuario: String,
        photoUrl: String,
        idUsuarioActual: String
    ) {
        Log.d("solicitud", "creado")
        deporappViewModel.crearP_EncuentroConHilos(
            idEncuentro,
            nombreUsuario,
            photoUrl,
            idUsuarioActual
        )
    }


}
