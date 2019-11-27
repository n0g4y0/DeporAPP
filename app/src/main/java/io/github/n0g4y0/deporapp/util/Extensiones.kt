package io.github.n0g4y0.deporapp.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import io.github.n0g4y0.deporapp.ui.MainActivity

fun Context.login(){
    val intent = Intent(this, MainActivity::class.java)
        .apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    startActivity(intent)
}

fun Activity.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()