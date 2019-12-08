package io.github.n0g4y0.deporapp.firebase.storage

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

private const val REFERENCIA_FOTOS_CANCHA = "fotos_cancha"

class StorageManager {

    private val firebaseStorage by lazy { FirebaseStorage.getInstance() }


    fun subirFoto(imagenSeleccionadoUri: Uri, enAccionDeExito: (String) -> Unit) {

        // referencia de la carpeta fotos, en FIREBASE, ahi es donde se subira la foto
        val referenciaFotos = firebaseStorage.getReference(REFERENCIA_FOTOS_CANCHA)

        // se usara la variable "lastpathSegment" como el nombre del archivo
        imagenSeleccionadoUri.lastPathSegment?.let { segmento ->
            // se obtiene el punto de referencia, donde se guardara la imagen
            val referenciaFoto = referenciaFotos.child(segmento)
            // almacena la imagen con PUTFILE(), y pasas el URI, y luego devuelve una instancia de UPLOADTASK
            referenciaFoto.putFile(imagenSeleccionadoUri)

                // llamamos a este metodo, para que nos devuelva la URL de la imagen, una vez esta se halla subido completamente
                .continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { tarea ->

                    val exception = tarea.exception
                    // si esta bien, devuelve la URL, sino , lanza una excepcion
                    if (!tarea.isSuccessful && exception != null) {

                        throw exception
                    }
                    return@Continuation referenciaFoto.downloadUrl
                })

                // adjunta un LISTENER para recibir una notificacion, cuando finalice la subida de la imagen.
                .addOnCompleteListener { tarea ->
                    // cuando se complete el proceso, devolvera el STRING de la URL, donde se encuentra la imagen:
                    if (tarea.isSuccessful) {

                        val descargaUri = tarea.result
                        enAccionDeExito(descargaUri.toString())

                    }
                }
        }


    }

}