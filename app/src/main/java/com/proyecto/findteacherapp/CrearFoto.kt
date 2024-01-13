package com.proyecto.findteacherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyecto.findteacherapp.Adapter.FotografiasAdapter
import com.proyecto.findteacherapp.Model.FotografiasModel

class CrearFoto : AppCompatActivity() {

    private lateinit var BotonAtrasImagenes: ImageView
    private lateinit var AccionAgregarImagen: ImageView
    private lateinit var AccionAgregarText: TextView
    private lateinit var RecyclerFotos: RecyclerView
    private lateinit var reference: DatabaseReference
    private lateinit var lista : ArrayList <FotografiasModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_foto)

        BotonAtrasImagenes = findViewById(R.id.BotonAtrasImagenes)
        AccionAgregarImagen = findViewById(R.id.AccionAgregarImagen)
        AccionAgregarText = findViewById(R.id.AccionAgregarText)
        RecyclerFotos = findViewById(R.id.RecyclerFotos)

        RecyclerFotos.layoutManager = LinearLayoutManager(this)
        RecyclerFotos.setHasFixedSize(true)
        RecyclerFotos.visibility = View.GONE

        lista = arrayListOf<FotografiasModel>()

        reference = FirebaseDatabase.getInstance().getReference("ImagenesPromocionales")

        reference.addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lista.clear()
                if (snapshot.exists()){
                    for (Snap in snapshot.children){
                        val data = Snap.getValue(FotografiasModel::class.java)
                        lista.add(data!!)
                    }
                    val Adapter = FotografiasAdapter(lista)
                    RecyclerFotos.adapter = Adapter
                    RecyclerFotos.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        BotonAtrasImagenes.setOnClickListener {
            val intent = Intent(this@CrearFoto, AccionesAdministrador::class.java)
            startActivity(intent)
            finish()
        }

        AccionAgregarText.setOnClickListener {
            val intent = Intent(this@CrearFoto, AgregarFotografias::class.java)
            startActivity(intent)
        }

        AccionAgregarImagen.setOnClickListener {
            val intent = Intent(this@CrearFoto, AgregarFotografias::class.java)
            startActivity(intent)
        }
    }
}