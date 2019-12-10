package io.github.n0g4y0.deporapp.firebase.firestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import io.github.n0g4y0.deporapp.firebase.auth.AutentificacionManager
import io.github.n0g4y0.deporapp.model.Anuncio
import io.github.n0g4y0.deporapp.model.Cancha


private const val COLECCION_CANCHAS = "canchas"
private const val COLECCION_ANUNCIOS = "anuncios"




// valores estaticos, canchas:
private const val CLAVE_ID = "id"
private const val CLAVE_TITULO = "titulo"
private const val CLAVE_FOTO_URL = "foto_url"
private const val CLAVE_UBICACION_LAT = "ubicacion_lat"
private const val CLAVE_UBICACION_LNG = "ubicacion_long"
private const val CLAVE_FUTBOL = "futbol"
private const val CLAVE_FUTSAL = "futsal"
private const val CLAVE_BASQUET = "basquet"
private const val CLAVE_VOLEY = "voley"
private const val CLAVE_FECHA = "fecha_timestamp"
private const val CLAVE_CREADOR = "autor"


// valores estaticos, anuncios:

private const val CLAVE_ID_ANUNCIO = "id"
private const val CLAVE_TITULO_ANUNCIO = "titulo"
private const val CLAVE_TITULO_DESCRIPCION = "descripcion"





private lateinit var registrosCanchas: ListenerRegistration
private lateinit var registrosAnuncios: ListenerRegistration

class FirestoreManager {

    private val authManager = AutentificacionManager()
    private val baseDeDato = FirebaseFirestore.getInstance()

    private val valoresCanchas = MutableLiveData<List<Cancha>>()

    private val valoresAnuncios = MutableLiveData<List<Anuncio>>()



    fun agregarCancha(titulo: String,
                      urlFoto: String,
                      ubicacionLat: Double,
                      ubicacionLng: Double,
                      siFutbol: Boolean,
                      siFutsal: Boolean,
                      siBasquet: Boolean,
                      siVoley: Boolean,
                      enAcciondeExito: () -> Unit, enAcciondeFracaso: () -> Unit ){

        val referenciaDocumento = baseDeDato.collection(COLECCION_CANCHAS).document()

        val cancha = HashMap<String,Any>()

        cancha[CLAVE_ID] = referenciaDocumento.id
        cancha[CLAVE_TITULO] = titulo
        cancha[CLAVE_FOTO_URL] = urlFoto
        cancha[CLAVE_UBICACION_LAT] = ubicacionLat
        cancha[CLAVE_UBICACION_LNG] = ubicacionLng
        cancha[CLAVE_FUTBOL] = siFutbol
        cancha[CLAVE_FUTSAL] = siFutsal
        cancha[CLAVE_BASQUET] = siBasquet
        cancha[CLAVE_VOLEY] = siVoley
        cancha[CLAVE_FECHA] = getTiempoActual()
        cancha[CLAVE_CREADOR] = authManager.getUsuarioActual()


        referenciaDocumento
            .set(cancha)
            .addOnSuccessListener { enAcciondeExito() }
            .addOnFailureListener { enAcciondeFracaso() }



    }

    fun enCambiosDeValorCanchas(): LiveData<List<Cancha>>{

        escucharPorCambiosEnValoresCanchas()
        return valoresCanchas

    }

    fun cambiosDeValorAnuncios(): LiveData<List<Anuncio>>{
        escucharPorCambiosEnValoresAnuncios()
        return valoresAnuncios
    }




    private fun escucharPorCambiosEnValoresCanchas() {

        registrosCanchas = baseDeDato.collection(COLECCION_CANCHAS)

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->
                // 4
                if (error != null || valor == null) {
                    return@EventListener
                }
                // 5
                if (valor.isEmpty) {

                    // 6
                    valoresCanchas.postValue(emptyList())
                } else {
                    // 7
                    val canchas = ArrayList<Cancha>()
                    // 8
                    for (doc in valor) {
                        // 9
                        val cancha = doc.toObject(Cancha::class.java)
                        canchas.add(cancha)
                    }
                    // 10
                    valoresCanchas.postValue(canchas)
                }
            })

    }

    private fun escucharPorCambiosEnValoresAnuncios() {
        registrosAnuncios = baseDeDato.collection(COLECCION_ANUNCIOS)

            .addSnapshotListener(EventListener<QuerySnapshot> { valor, error ->
                // 4
                if (error != null || valor == null) {
                    return@EventListener
                }
                // 5
                if (valor.isEmpty) {

                    // 6
                    valoresAnuncios.postValue(emptyList())
                } else {
                    // 7
                    val anuncios = ArrayList<Anuncio>()
                    // 8
                    for (doc in valor) {
                        // 9
                        val anuncio = doc.toObject(Anuncio::class.java)
                        anuncios.add(anuncio)
                    }
                    // 10
                    valoresAnuncios.postValue(anuncios)
                }
            })
    }




    private fun getTiempoActual() = System.currentTimeMillis()



}