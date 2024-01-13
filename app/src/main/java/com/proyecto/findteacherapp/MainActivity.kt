package com.proyecto.findteacherapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        val animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba)
        val animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo)

        val imageView = findViewById<ImageView>(R.id.img_logo)
        val textView = findViewById<TextView>(R.id.textView_logo)

        imageView.animation = animacion1
        textView.animation = animacion2

        Handler().postDelayed({
            val intent = Intent(this@MainActivity, VentanaPrincipal::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}