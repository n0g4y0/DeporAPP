package io.github.n0g4y0.deporapp.firebase.corutinas

/*
* esta clase nos sirve para mantener o sostener los resultados de una operacion de FIRESTORE
*
* */

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>() // Status success and data of the result
    data class Error(val exception: Exception) : Result<Nothing>() // Status Error an error message
    data class Canceled(val exception: Exception?) : Result<Nothing>() // Status Canceled

    // string method to display a result for debugging
    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Exitoso[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Canceled -> "Cancelado[exception=$exception]"
        }
    }
}