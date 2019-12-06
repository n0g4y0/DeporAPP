package io.github.n0g4y0.deporapp.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.text.format.DateFormat
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

class SeleccionArchivo(val context: Context) {

    fun crearArchivoTemporal(): File {
        val fileName = DateFormat.format("ddMMyyy_hhmmss", Date()).toString()
        return File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "$fileName.jpg")
    }

    fun UriDelArchivo(archivo: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "io.github.n0g4y0.deporapp.fileprovider",
            archivo)
    }

}