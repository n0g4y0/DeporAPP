package io.github.n0g4y0.deporapp.util

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.viewmodel.DeporappViewModel
import java.util.*


private const val ARG_FECHA = "fecha"

class TimePickerFragment: DialogFragment() {

    private val deporappViewModel: DeporappViewModel by navGraphViewModels(R.id.nav_graph)


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val horaListener = TimePickerDialog.OnTimeSetListener{
                _: TimePicker, horaDia: Int, minuto: Int ->
            val horaResultado : Date = GregorianCalendar(0,0,0,horaDia,minuto).time


            deporappViewModel.setHoraEncuentro(horaResultado)

        }



        val calendar = Calendar.getInstance()
        val initialHour = calendar.get(Calendar.HOUR)
        val initialMinute = calendar.get(Calendar.MINUTE)
        val is24HrView = true


        return TimePickerDialog(
            requireContext(),
            horaListener,
            initialHour,
            initialMinute,
            is24HrView
        )


    }



}