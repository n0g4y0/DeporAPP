package io.github.n0g4y0.deporapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.squareup.picasso.Picasso
import io.github.n0g4y0.deporapp.R
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {


    private lateinit var botonLogin : Button
    private lateinit var botonRegistro : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // añadiendo la imagen PNG al ImageView:
        Picasso.get()
            .load(R.drawable.deporapp_intro_new)
            .resize(1000,1000)
            .onlyScaleDown()
            .centerCrop()
            .into(imagen_intro)

        botonLogin = findViewById(R.id.button_show_login)
        botonRegistro = findViewById(R.id.button_new_account)

        botonLogin.setOnClickListener {
            val myintent = Intent(this, LoginActivity::class.java)
            startActivity(myintent)
        }

        botonRegistro.setOnClickListener {
            val myintent = Intent(this, RegisterActivity::class.java)
            startActivity(myintent)
        }

    }

}
