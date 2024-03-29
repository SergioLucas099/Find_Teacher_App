package com.proyecto.findteacherapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class DetallesFotografia : AppCompatActivity() {
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_fotografia)

        reference = FirebaseDatabase.getInstance().getReference("ImagenesPromocionales")

        val AtrasImgPromoDetalles = findViewById<ImageView>(R.id.AtrasImgPromoDetalles)
        val ImgDetallesPromo = findViewById<ImageView>(R.id.ImgDetallesPromo)
        val BorrarImgDetalles = findViewById<ImageView>(R.id.BorrarImgDetalles)

        var url: String? = intent.getStringExtra("Url")
        var id: String? = intent.getStringExtra("Id")

        Picasso.get().load(url).into(ImgDetallesPromo)

        AtrasImgPromoDetalles.setOnClickListener {
            val intent = Intent(this@DetallesFotografia, CrearFoto::class.java)
            startActivity(intent)
            finish()
        }

        BorrarImgDetalles.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Advertencia")
            builder.setMessage("¿Esta seguro que desea eliminar esta imagen?")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                reference.child(id.toString()).removeValue().addOnCompleteListener{
                    Toast.makeText(this, "Se ha eliminado la imagen", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@DetallesFotografia, CrearFoto::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this, "Error añ eliminar la imagen", Toast.LENGTH_SHORT).show()
                }
            })
            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "Acción Cancelada", Toast.LENGTH_SHORT).show()
            })
            builder.show()
        }
    }
}