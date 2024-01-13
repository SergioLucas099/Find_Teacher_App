package com.proyecto.findteacherapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso

class DetallesAnuncio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_anuncio)

        val AtrasDetallesAnuncio = findViewById<ImageView>(R.id.AtrasDetallesAnuncio)
        val TxtNombreDetallesAnuncio = findViewById<TextView>(R.id.TxtNombreDetallesAnuncio)
        val TxtUbicacionDetallesAnuncio = findViewById<TextView>(R.id.TxtUbicacionDetallesAnuncio)
        val TxtAsignaturaDetallesAnuncio = findViewById<TextView>(R.id.TxtAsignaturaDetallesAnuncio)
        val TxtInformacionDetallesAnuncio = findViewById<TextView>(R.id.TxtInformacionDetallesAnuncio)
        val ImgDocenteDetallesAnuncio = findViewById<ImageView>(R.id.ImgDocenteDetallesAnuncio)
        val TxtTelefonoDetallesAnuncio = findViewById<TextView>(R.id.TxtTelefonoDetallesAnuncio)
        val AccionNumeroDetallesAnuncio = findViewById<LinearLayout>(R.id.AccionNumeroDetallesAnuncio)

        var Id: String? = intent.getStringExtra("Id")
        var Nombre: String? = intent.getStringExtra("Nombre")
        var Telefono: String? = intent.getStringExtra("Telefono")
        var Informacion: String? = intent.getStringExtra("Informacion")
        var Ciudad: String? = intent.getStringExtra("Ciudad")
        var Asignatura: String? = intent.getStringExtra("Asignatura")
        var Url: String? = intent.getStringExtra("Url")

        AtrasDetallesAnuncio.setOnClickListener {
            val intent = Intent(this@DetallesAnuncio, VentanaPrincipal::class.java)
            startActivity(intent)
            finish()
        }

        TxtNombreDetallesAnuncio.setText(Nombre)
        TxtUbicacionDetallesAnuncio.setText(Ciudad)
        TxtInformacionDetallesAnuncio.setText(Informacion)
        TxtAsignaturaDetallesAnuncio.setText(Asignatura)
        TxtTelefonoDetallesAnuncio.setText(Telefono)
        Picasso.get().load(Url).into(ImgDocenteDetallesAnuncio)

        AccionNumeroDetallesAnuncio.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=+57$Telefono")
            startActivity(intent)
        }
    }
}