package io.github.n0g4y0.deporapp.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


    fun getEscalarBitMap(path: String, activity: Activity): Bitmap {
        val size = Point()
        activity.windowManager.defaultDisplay.getSize(size)
        return getEscalarBitMap(path, size.x, size.y)
    }


    fun getEscalarBitMap(path: String, destWidth: Int, destHeight: Int): Bitmap {
        // Read in the dimensions of the image on disk
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        val srcWidth = options.outWidth.toFloat()
        val srcHeight = options.outHeight.toFloat()
        // Figure out how much to scale down by
        var inSampleSize = 1
        if (srcHeight > destHeight || srcWidth > destWidth) {
            val heightScale = srcHeight / destHeight
            val widthScale = srcWidth / destWidth
            val sampleScale = if (heightScale > widthScale) {
                heightScale
            } else {
                widthScale
            }
            inSampleSize = Math.round(sampleScale)
        }
        options = BitmapFactory.Options()
        options.inSampleSize = inSampleSize
        // Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options)
    }

/*
*
* FALTA VERIFICAR LS SIGUIENTES LINEAS, para manipular la GALERIA Y DEMAS:
*
* */


object ImageUtils {
    // 2
    fun guardarBitmapAlArchivo(
        context: Context,
        bitmap: Bitmap,
        filename: String) {

        // 3
        val stream = ByteArrayOutputStream()
        // 4
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        // 5
        val bytes = stream.toByteArray()
        // 6
        guardarBytesAlArchivo(context, bytes, filename)
    }

    // 7
    private fun guardarBytesAlArchivo(
        context: Context,
        bytes: ByteArray,
        filename: String) {

        val outputStream: FileOutputStream
        // 8
        try {
            // 9
            outputStream = context.openFileOutput(
                filename,
                Context.MODE_PRIVATE
            )
            // 10
            outputStream.write(bytes)
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun cargarBitmapDesdeArchivo(context: Context, nombreArchivo: String): Bitmap? {

        val rutaArchivo = File(context.filesDir, nombreArchivo).absolutePath

        return BitmapFactory.decodeFile(rutaArchivo)

    }

    @Throws(IOException::class)
    fun crearUnicoArchivoImagen(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())

        val nombreArchivo = "deporapp_" + timeStamp + "_"
        val directorioArchivo = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(nombreArchivo, ".jpg", directorioArchivo)
    }

    /*
    *
    * calcula el tamaño y el ancho adecuado de las fotos que se sacan.
    *
    * */

    private fun calcularTamanioMuestra(width: Int,height: Int,reqWidth: Int,reqHeight: Int): Int {

        var tamanioMuestra = 1

        if (height > reqHeight || width > reqWidth){

            val halfHeight = height / 2
            val halfWith = width / 2

            while (halfHeight / tamanioMuestra >= reqHeight && halfWith / tamanioMuestra >= reqWidth){
                tamanioMuestra *= 2
            }
        }

        return tamanioMuestra

    }

    /*
    * este metodo se llama cuando una imagen necesita ser transformada:
    * */
    fun transformarArchivoSegunTamanio(rutaArchivo: String,ancho: Int, alto: Int): Bitmap {

        //1
        val opciones = BitmapFactory.Options()

        opciones.inJustDecodeBounds = true
        BitmapFactory.decodeFile(rutaArchivo, opciones)

        //2
        opciones.inSampleSize = calcularTamanioMuestra(
            opciones.outWidth,
            opciones.outHeight,
            ancho,
            alto
        )
        //3
        opciones.inJustDecodeBounds = false

        //4
        return BitmapFactory.decodeFile(rutaArchivo,opciones)

    }

    /*
    *
    * */

    fun transformarUriAlTamanio(uri: Uri, ancho: Int, alto: Int, context: Context): Bitmap? {
        var entrada : InputStream? = null

        try {
            val opciones : BitmapFactory.Options
            //1
            entrada = context.contentResolver.openInputStream(uri)
            //2
            if (entrada != null){
                //3
                opciones = BitmapFactory.Options()
                opciones.inJustDecodeBounds = false
                BitmapFactory.decodeStream(entrada, null, opciones)

                //4
                entrada.close()
                entrada = context.contentResolver.openInputStream(uri)

                if (entrada != null){
                    //5
                    opciones.inSampleSize =
                        calcularTamanioMuestra(
                            opciones.outWidth,
                            opciones.outHeight,
                            ancho,
                            alto
                        )
                    opciones.inJustDecodeBounds = false

                    val bitmap = BitmapFactory.decodeStream(entrada,null,opciones)
                    entrada.close()

                    return bitmap
                }
            }

            return null
        }catch (e: Exception){
            return null
        }finally {
            //6
            entrada?.close()
        }
    }


    fun getImageUriFromBitmap(contexto: Context, bitmap: Bitmap): Uri {

        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes)

        val ruta = MediaStore.Images.Media.insertImage(contexto.contentResolver, bitmap, "titulo",null)

        return Uri.parse(ruta.toString())

    }

}
