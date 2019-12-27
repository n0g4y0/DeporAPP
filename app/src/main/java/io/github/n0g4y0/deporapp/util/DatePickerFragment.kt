package io.github.n0g4y0.deporapp.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import java.util.*

private const val ARG_FECHA = "fecha"

class DatePickerFragment: DialogFragment() {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val fechaListener = DatePickerDialog.OnDateSetListener {
                _: DatePicker, anio: Int, mes: Int, dia: Int ->
            val fechaResultado : Date = GregorianCalendar(anio, mes, dia).time

            deporappViewModel.setFechaEncuentro(fechaResultado)


        }


        val calendario = Calendar.getInstance()
        val anioInicial = calendario.get(Calendar.YEAR)
        val mesInicial = calendario.get(Calendar.MONTH)
        val diaInicial = calendario.get(Calendar.DAY_OF_MONTH)


        return DatePickerDialog(
            requireContext(),
            // el valor NULL es porque todavia no le cambiamos la fecha, los otros datos, son los POR DEFECTO.
            fechaListener,
            anioInicial,
            mesInicial,
            diaInicial
        )


    }

}