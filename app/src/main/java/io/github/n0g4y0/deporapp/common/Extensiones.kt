package io.github.n0g4y0.deporapp.common

import android.content.Context
import android.content.Intent
import io.github.n0g4y0.deporapp.activity.MainActivity

fun Context.login(){
    val intent = Intent(this,MainActivity::class.java)
        .apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    startActivity(intent)
}