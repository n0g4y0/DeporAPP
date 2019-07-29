package io.github.n0g4y0.deporapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.n0g4y0.deporapp.registerlogin.RegisterActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Menu Principal"
    }
}
