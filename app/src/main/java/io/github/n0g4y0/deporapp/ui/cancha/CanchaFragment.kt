package io.github.n0g4y0.deporapp.ui.cancha

import android.net.Uri
import android.widget.*
import androidx.fragment.app.Fragment
import io.github.n0g4y0.deporapp.R
import io.github.n0g4y0.deporapp.model.Cancha
import java.io.File

private const val TAG = "CanchaFragment"
private const val ARG_CANCHA_ID = "cancha_id"
private const val FECHA_DIALOGO = "fechaDialogo"
private const val FECHA_SOLICITUD = 0
private const val FOTO_SOLICITUD = 2

//ponemos el diseño en el constructor, para evitar sobreESCRIBIR la funcion ONCREATEVIEW()

class CanchaFragment : Fragment(R.layout.fragment_cancha) {

    private lateinit var cancha : Cancha
    private lateinit var archivoFoto : File
    private lateinit var UriFoto : Uri
    private lateinit var tituloCancha : EditText
    private lateinit var ubicacionCancha : Button
    private lateinit var fotoCancha : Button
    private lateinit var futbolCancha : CheckBox
    private lateinit var futsalCancha : CheckBox
    private lateinit var basquetCancha : CheckBox
    private lateinit var voleyCancha : CheckBox

    private lateinit var canchaFoto : ImageView
    private lateinit var canchaCamara : ImageButton


}