package com.proyecto.findteacherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyecto.findteacherapp.Adapter.AnuncioAdapter
import com.proyecto.findteacherapp.Model.AnuncioModel

class AccionAreaConocimiento : AppCompatActivity() {

    private lateinit var lista : ArrayList<AnuncioModel>
    private lateinit var reference: DatabaseReference
    private lateinit var RecyclerAreaC: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accion_area_conocimiento)

        val AtrasAccionesA = findViewById<ImageView>(R.id.AtrasAccionesA)
        val ImgFondoMate = findViewById<View>(R.id.ImgFondoMate)
        val ImgFondoIdiomas = findViewById<View>(R.id.ImgFondoIdiomas)
        val ImgFondoDeportes = findViewById<View>(R.id.ImgFondoDeportes)
        val ImgFondoArte = findViewById<View>(R.id.ImgFondoArte)
        val ImgFondoGeografia = findViewById<View>(R.id.ImgFondoGeografia)
        val ImgMate = findViewById<ImageView>(R.id.ImgMate)
        val ImgIdiomas = findViewById<ImageView>(R.id.ImgIdiomas)
        val ImgDeportes = findViewById<ImageView>(R.id.ImgDeportes)
        val ImgArte = findViewById<ImageView>(R.id.ImgArte)
        val ImgGeografia = findViewById<ImageView>(R.id.ImgGeografia)
        val TxtAC = findViewById<TextView>(R.id.TxtAC)
        RecyclerAreaC = findViewById(R.id.RecyclerAreaC)
        val sv_area_conocimiento_anuncio = findViewById<
                androidx.appcompat.widget.SearchView>(R.id.sv_area_conocimiento_anuncio)
        reference = FirebaseDatabase.getInstance().getReference("Anuncio")
        var Area: String? = intent.getStringExtra("Dato")

        RecyclerAreaC.layoutManager = LinearLayoutManager(this)
        RecyclerAreaC.setHasFixedSize(true)
        lista = arrayListOf<AnuncioModel>()

        AtrasAccionesA.setOnClickListener {
            val intent = Intent(this@AccionAreaConocimiento, VentanaPrincipal::class.java)
            startActivity(intent)
            finish()
        }

        if(Area.equals("Matematicas")){
            ImgMate.visibility = View.VISIBLE
            ImgFondoMate.visibility = View.VISIBLE
            TxtAC.setText("Matematicas")

            reference = FirebaseDatabase.getInstance().getReference("Matematicas")

            reference.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(AnuncioModel::class.java)
                            lista.add(data!!)
                        }

                       val Adapter = AnuncioAdapter(lista)
                        RecyclerAreaC.adapter = Adapter
                        RecyclerAreaC.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        else if(Area.equals("Idiomas")){
            ImgIdiomas.visibility = View.VISIBLE
            ImgFondoIdiomas.visibility = View.VISIBLE
            TxtAC.setText("Idiomas")

            reference = FirebaseDatabase.getInstance().getReference("Idiomas")

            reference.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(AnuncioModel::class.java)
                            lista.add(data!!)
                        }

                        val Adapter = AnuncioAdapter(lista)
                        RecyclerAreaC.adapter = Adapter
                        RecyclerAreaC.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        else if(Area.equals("Deportes")){
            ImgDeportes.visibility = View.VISIBLE
            ImgFondoDeportes.visibility = View.VISIBLE
            TxtAC.setText("Deportes")

            reference = FirebaseDatabase.getInstance().getReference("Deportes")

            reference.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(AnuncioModel::class.java)
                            lista.add(data!!)
                        }

                        val Adapter = AnuncioAdapter(lista)
                        RecyclerAreaC.adapter = Adapter
                        RecyclerAreaC.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        else if(Area.equals("Arte")){
            ImgArte.visibility = View.VISIBLE
            ImgFondoArte.visibility = View.VISIBLE
            TxtAC.setText("Arte")

            reference = FirebaseDatabase.getInstance().getReference("Arte")

            reference.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(AnuncioModel::class.java)
                            lista.add(data!!)
                        }

                        val Adapter = AnuncioAdapter(lista)
                        RecyclerAreaC.adapter = Adapter
                        RecyclerAreaC.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        else if(Area.equals("Geografia")){
            ImgGeografia.visibility = View.VISIBLE
            ImgFondoGeografia.visibility = View.VISIBLE
            TxtAC.setText("Geografia")

            reference = FirebaseDatabase.getInstance().getReference("Geografia")

            reference.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    if (snapshot.exists()){
                        for (Snap in snapshot.children){
                            val data = Snap.getValue(AnuncioModel::class.java)
                            lista.add(data!!)
                        }

                        val Adapter = AnuncioAdapter(lista)
                        RecyclerAreaC.adapter = Adapter
                        RecyclerAreaC.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        sv_area_conocimiento_anuncio.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                buscar(s)
                return true
            }
        })
    }

    private fun buscar(s: String) {
        val milista = java.util.ArrayList<AnuncioModel>()
        for (obj in lista) {
            if (obj.Nombre?.lowercase()?.contains(s.lowercase()) == true){
                milista.add(obj)
            }
        }
        val adapter = AnuncioAdapter(milista)
        RecyclerAreaC.setAdapter(adapter)
    }
}