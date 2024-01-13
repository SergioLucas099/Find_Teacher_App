package com.proyecto.findteacherapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.proyecto.findteacherapp.Adapter.AnuncioAdapter
import com.proyecto.findteacherapp.Model.AnuncioModel
import com.proyecto.findteacherapp.includes.MiToolbar
import java.util.ArrayList

class VentanaPrincipal : AppCompatActivity() {

    private lateinit var lista : ArrayList <AnuncioModel>
    private lateinit var reference: DatabaseReference
    private lateinit var rv_anuncios: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventana_principal)

        MiToolbar.show(this, "Find Teacher App", false)

        val sv_profesor = findViewById<androidx.appcompat.widget.SearchView>(R.id.sv_profesor)
        rv_anuncios = findViewById(R.id.rv_anuncios)
        val imageSlider = findViewById<ImageSlider>(R.id.imageSlider)
        val AccionMatematicas = findViewById<LinearLayout>(R.id.AccionMatematicas)
        val AccionIdiomas = findViewById<LinearLayout>(R.id.AccionIdiomas)
        val AccionDeporte = findViewById<LinearLayout>(R.id.AccionDeporte)
        val AccionArte = findViewById<LinearLayout>(R.id.AccionArte)
        val AccionGeografias = findViewById<LinearLayout>(R.id.AccionGeografias)
        reference = FirebaseDatabase.getInstance().getReference("Anuncio")

        val imageList = ArrayList<SlideModel>()

        rv_anuncios.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rv_anuncios.setHasFixedSize(true)
        lista = arrayListOf<AnuncioModel>()

        ///////////////////Imagenes Promocionales/////////////////////////
        FirebaseDatabase.getInstance().reference.child("ImagenesPromocionales")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        imageList.add(SlideModel(data.child("Url").value.toString(), ScaleTypes.FIT))
                        imageSlider.setImageList(imageList, ScaleTypes.FIT)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        ///////////////////Matematicas/////////////////////////
        AccionMatematicas.setOnClickListener {
            val intent = Intent(this, AccionAreaConocimiento::class.java)
            intent.putExtra("Dato", "Matematicas")
            startActivity(intent)
            finish()
        }

        ///////////////////Idiomas/////////////////////////
        AccionIdiomas.setOnClickListener {
            val intent = Intent(this, AccionAreaConocimiento::class.java)
            intent.putExtra("Dato", "Idiomas")
            startActivity(intent)
            finish()
        }

        ///////////////////Deportes/////////////////////////
        AccionDeporte.setOnClickListener {
            val intent = Intent(this, AccionAreaConocimiento::class.java)
            intent.putExtra("Dato", "Deportes")
            startActivity(intent)
            finish()
        }

        ///////////////////Arte/////////////////////////
        AccionArte.setOnClickListener {
            val intent = Intent(this, AccionAreaConocimiento::class.java)
            intent.putExtra("Dato", "Arte")
            startActivity(intent)
            finish()
        }

        ///////////////////Geografia/////////////////////////
        AccionGeografias.setOnClickListener {
            val intent = Intent(this, AccionAreaConocimiento::class.java)
            intent.putExtra("Dato", "Geografia")
            startActivity(intent)
            finish()
        }

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lista.clear()
                if (snapshot.exists()){
                    for (Snap in snapshot.children){
                        val data = Snap.getValue(AnuncioModel::class.java)
                        lista.add(data!!)
                    }
                    val Adapter = AnuncioAdapter(lista)
                    rv_anuncios.adapter = Adapter
                    rv_anuncios.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        sv_profesor.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
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
        val milista = ArrayList<AnuncioModel>()
        for (obj in lista) {
            if (obj.Nombre?.lowercase()?.contains(s.lowercase()) == true){
                milista.add(obj)
            }
        }
        val adapter = AnuncioAdapter(milista)
        rv_anuncios.setAdapter(adapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.InicioSesion) {
            val intent = Intent(this@VentanaPrincipal, InicioSesion::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        val preferences: SharedPreferences
        preferences = getSharedPreferences("typeUser", MODE_PRIVATE)
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            val user = preferences.getString("user", "")
            if (user.equals("Administrador")){
                val intent = Intent(this, AccionesAdministrador::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}