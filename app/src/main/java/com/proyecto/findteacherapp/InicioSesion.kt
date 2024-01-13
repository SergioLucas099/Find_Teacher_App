package com.proyecto.findteacherapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class InicioSesion : AppCompatActivity() {

    private lateinit var UsuarioEditText: EditText
    private lateinit var ContaseñaEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        val volver = findViewById<ImageView>(R.id.volver)
        UsuarioEditText = findViewById(R.id.UsuarioEditText)
        ContaseñaEditText = findViewById(R.id.ContaseñaEditText)
        val BotonInicioSesion = findViewById<Button>(R.id.BotonInicioSesion)

        volver.setOnClickListener {
            val intent = Intent(this@InicioSesion, VentanaPrincipal::class.java)
            startActivity(intent)
            finish()
        }

        BotonInicioSesion.setOnClickListener {
            IniciarSesion()
        }
    }

    private fun IniciarSesion(){
        val preferences: SharedPreferences
        preferences = getSharedPreferences("typeUser", MODE_PRIVATE)
        val editor = preferences.edit()

        if(!UsuarioEditText.text.toString().isEmpty() && !ContaseñaEditText.text.toString().isEmpty()){
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(UsuarioEditText.text.toString(), ContaseñaEditText.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this@InicioSesion, AccionesAdministrador::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        editor.putString("user", "Administrador")
                        editor.apply()
                        startActivity(intent)
                        finish()
                    }else {
                        Toast.makeText(this, "Este usuario no esta registrado", Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(this, "Debe ingresar la información completa para continuar", Toast.LENGTH_SHORT).show()
        }
    }
}