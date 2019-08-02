package io.github.n0g4y0.deporapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import io.github.n0g4y0.deporapp.registerlogin.LoginActivity
import io.github.n0g4y0.deporapp.registerlogin.RegisterActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // añadiendo la imagen PNG al ImageView:
        Picasso.get()
            .load(R.drawable.deporapp_intro)
            .resize(1000,1000)
            .onlyScaleDown()
            .centerCrop()
            .into(imagen_intro)

    }

    fun openRegister(view:View){


        val myintent = Intent(this,RegisterActivity::class.java)
        startActivity(myintent)
    }
    fun openLogin(view:View){


        val myintent = Intent(this, LoginActivity::class.java)
        startActivity(myintent)
    }
}
