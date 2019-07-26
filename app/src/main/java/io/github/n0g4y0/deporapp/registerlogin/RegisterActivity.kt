package io.github.n0g4y0.deporapp.registerlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.n0g4y0.deporapp.R

class RegisterActivity : AppCompatActivity() {

    companion object {
        val NAME_ACTIVITY:String = "Intro Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.title = NAME_ACTIVITY
    }
}
