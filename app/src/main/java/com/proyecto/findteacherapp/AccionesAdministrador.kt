package com.proyecto.findteacherapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.proyecto.findteacherapp.includes.MiToolbar

class AccionesAdministrador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acciones_administrador)

        MiToolbar.show(this, "Acciones Administrador", false)

        val FotoAdmin = findViewById<LinearLayout>(R.id.FotoAdmin)
        val AnuncioAdmin = findViewById<LinearLayout>(R.id.AnuncioAdmin)

        FotoAdmin.setOnClickListener {
            val intent = Intent(this@AccionesAdministrador, CrearFoto::class.java)
            startActivity(intent)
        }

        AnuncioAdmin.setOnClickListener {
            val intent = Intent(this@AccionesAdministrador, CrearAnuncio::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_admin, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.Salir) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Advertencia")
            builder.setMessage("¿Desea cerrar sesión?")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, InicioSesion::class.java)
                startActivity(intent)
            })
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "Acción Cancelada", Toast.LENGTH_SHORT).show()
            })
            builder.show()
        }
        return super.onOptionsItemSelected(item)
    }
}