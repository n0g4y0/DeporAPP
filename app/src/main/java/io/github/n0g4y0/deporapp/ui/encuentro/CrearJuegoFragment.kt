package io.github.n0g4y0.deporapp.ui.encuentro


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Cancha
import io.github.n0g4y0.deporapp.model.P_Equipo
import io.github.n0g4y0.deporapp.util.DateUtils
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import kotlinx.android.synthetic.main.fragment_crear_juego.*
import java.text.SimpleDateFormat
import java.util.*


class CrearJuegoFragment : Fragment(R.layout.fragment_crear_juego) {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    var fechaActual: Date? = null

    var horaActual: Date? = null

    var canchaEncuentro: Cancha? = null

    var equipoDelUsuarioActual: P_Equipo? = null

    private val fechaUtils = DateUtils()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFechaActualEncuentro()
        getHoraActualEncuentro()
        getCanchaDelEncuentro()
        getEquipoDelUsuarioActual()



        et_cancha_encuentro.setOnClickListener {
            findNavController().navigate(R.id.seleccionCanchaFragment)
        }


        et_fecha_encuentro.setOnClickListener {
            findNavController().navigate(R.id.datePickerFragment)
        }

        et_hora_encuentro.setOnClickListener {
            findNavController().navigate(R.id.timePickerFragment)
        }

        et_mi_equipo.setOnClickListener {
            findNavController().navigate(R.id.seleccionMiEquipoFragment)
        }

        btn_Crear_encuentro.setOnClickListener {
            guardarEncuentro()
        }

        btn_cancelar_encuentro.setOnClickListener {

            findNavController().popBackStack()

        }

    }

    private fun guardarEncuentro() {

        val nombreEncuentro = et_nombre_encuentro.text.toString().trim()
        val idCancha = canchaEncuentro?.id
        val fecha = fechaActual?.time
        val hora = horaActual?.time
        val cupos = et_cupo_encuentro.text.toString().toInt()
        val nota_adicional = et_nota_encuentro.text.toString().trim()
        val deporte_practicado = spinner_deporte_encuentro.selectedItem.toString()
        val seraPrivado = tipo_encuentro.isChecked

        val fk_cancha_lat = canchaEncuentro?.ubicacion_lat
        val fk_cancha_lng = canchaEncuentro?.ubicacion_long

        val fk_usuario_nick = deporappViewModel.getApodoUsuarioActual()
        val fk_usuario_foto_url = deporappViewModel.getPhotoUrlUsuarioActual()

        AlertDialog.Builder(requireContext())
            .setMessage("Crear Encuentro?")
            .setPositiveButton("OK"){ _,_ ->

                deporappViewModel?.let {
                    it.agregarEncuentro(
                        nombreEncuentro,
                        idCancha!!,
                        fecha!!,
                        hora!!,
                        cupos,
                        nota_adicional,
                        deporte_practicado,
                        seraPrivado,
                        fk_cancha_lat!!,
                        fk_cancha_lng!!,
                        fk_usuario_nick,
                        fk_usuario_foto_url
                        )
                    findNavController().popBackStack(R.id.listaJuegosFragment,false)

                }
            }
            .setNegativeButton("Cancelar",null)
            .create().show()


    }

    private fun getFechaActualEncuentro(){

        deporappViewModel.fechaEncuentro.observe(this, Observer { fechaActualEncuentro ->
                fechaActual = fechaActualEncuentro
                et_fecha_encuentro.setText(fechaUtils.convertirFechaAString(fechaActual!!))


        })
    }

    private fun getHoraActualEncuentro(){

        deporappViewModel.horaEncuentro.observe(this, Observer { horaActualEncuentro ->
            horaActual = horaActualEncuentro
            et_hora_encuentro.setText(fechaUtils.convertirHoraAString(horaActual!!))


        })

    }

    private fun getCanchaDelEncuentro(){

        deporappViewModel.idCanchaEncuentro.observe(this, Observer { canchaDelEncuentro ->
            canchaEncuentro = canchaDelEncuentro
            et_cancha_encuentro.setText(canchaEncuentro!!.titulo)
        })

    }

    private fun getEquipoDelUsuarioActual(){
        deporappViewModel.idEquipoEncuentro.observe(this, Observer {equipoDelUsuario ->
            equipoDelUsuarioActual = equipoDelUsuario
            et_mi_equipo.setText(equipoDelUsuario.nombre_equipo)

        })
    }






}
