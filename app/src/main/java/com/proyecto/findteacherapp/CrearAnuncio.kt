package com.proyecto.findteacherapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class CrearAnuncio : AppCompatActivity() {

    private lateinit var ImgAnuncio: ImageView
    private lateinit var UrlFoto: TextView
    private lateinit var EditTextNombre: EditText
    private lateinit var EditTextNumeroTelefonico: EditText
    private lateinit var TextCiudad: AutoCompleteTextView
    private lateinit var TextAsignatura: AutoCompleteTextView
    private lateinit var EditTextInformacion: EditText
    private lateinit var reference: DatabaseReference
    private lateinit var reference1: DatabaseReference
    private val mStorageRef = FirebaseStorage.getInstance().reference
    private val TAG = "FirebaseStorageManager"

    companion object{
        const val REQUEST_FROM_CAMERA = 1001
        const val REQUEST_FROM_GALERY = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_anuncio)

        reference = FirebaseDatabase.getInstance().getReference().child("Anuncio")
        ImgAnuncio = findViewById(R.id.ImgAnuncio)
        val AtrasCrearAnuncio = findViewById<ImageView>(R.id.AtrasCrearAnuncio)
        val SeleccionGaleria = findViewById<LinearLayout>(R.id.SeleccionGaleria)
        val SeleccionCamara = findViewById<LinearLayout>(R.id.SeleccionCamara)
        EditTextNombre = findViewById(R.id.EditTextNombre)
        EditTextNumeroTelefonico = findViewById(R.id.EditTextNumeroTelefonico)
        TextCiudad = findViewById(R.id.TextCiudad)
        TextAsignatura = findViewById(R.id.TextAsignatura)
        EditTextInformacion = findViewById(R.id.EditTextInformacion)
        val SubirInformacion = findViewById<Button>(R.id.SubirInformacion)
        UrlFoto = findViewById(R.id.UrlFoto)

        val items = listOf("Chiquinquirá","Quipama","Briceño", "Caldas", "La Uvita", "Jenesano",
            "Muzo", "San Pablo de Borbur", "Saboya", "La Victoria", "Tasco",
            "Pauna", "San Miguel de Sema", "Coper", "Buenavista", "San Mateo",
            "Maripi", "Otanche", "Tununguá", "Úmbita", "Toca")
        val adapter = ArrayAdapter(this, R.layout.list_item,items)

        val items1 = listOf("Matematicas", "Idiomas", "Deportes", "Arte", "Geografia")
        val adapter1 = ArrayAdapter(this, R.layout.list_item,items1)

        TextCiudad.setAdapter(adapter)
        TextAsignatura.setAdapter(adapter1)

        TextCiudad.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->

            val itemSelected = parent.getItemAtPosition(position)
            Toast.makeText(this,"Item: $itemSelected",Toast.LENGTH_SHORT).show()
        }

        TextAsignatura.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->

            val itemSelected = parent.getItemAtPosition(position)
            Toast.makeText(this,"Item: $itemSelected",Toast.LENGTH_SHORT).show()
        }

        val con = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = con.activeNetworkInfo

        AtrasCrearAnuncio.setOnClickListener {
            val intent = Intent(this@CrearAnuncio, AccionesAdministrador::class.java)
            startActivity(intent)
            finish()
        }

        SeleccionGaleria.setOnClickListener {
            AbrirGaleria()
        }

        SeleccionCamara.setOnClickListener {
            TomarFoto()
        }

        SubirInformacion.setOnClickListener {
            if(networkInfo!=null && networkInfo.isConnected){
                SubirInformacion()
            }else{
                Toast.makeText(this,"No hay conexión a internet\nVerifique el acceso a internet e intente nuevamente",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun AbrirGaleria(){
        ImagePicker.with(this).galleryOnly()
            .crop()
            .start(AgregarFotografias.REQUEST_FROM_GALERY)
    }

    private fun TomarFoto(){
        ImagePicker.with(this).cameraOnly()
            .crop()
            .start(AgregarFotografias.REQUEST_FROM_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when (requestCode){
                AgregarFotografias.REQUEST_FROM_CAMERA -> {
                    ImgAnuncio.setImageURI(data!!.data)
                    uploadImage(this, data.data!!)
                }
                AgregarFotografias.REQUEST_FROM_GALERY -> {
                    ImgAnuncio.setImageURI(data!!.data)
                    uploadImage(this, data.data!!)
                }
            }
        }
    }

    private fun uploadImage(mContext: Context, imageURI: Uri){
        val ImagenFileName = "FotoAnuncio ${System.currentTimeMillis()}"
        val uploadTask = mStorageRef.child(ImagenFileName).putFile(imageURI)
        uploadTask.addOnSuccessListener {
            Log.e(TAG, "Imagen cargada con éxito")
            val DescargarUrlImagen = mStorageRef.child(ImagenFileName).downloadUrl
            DescargarUrlImagen.addOnSuccessListener {
                UrlFoto.setText("$it")
            }.addOnFailureListener{
                Toast.makeText(this, "No se pudo cargar la Url de la imagen", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener{
            Log.e(TAG, "Carga de imagen fallida ${it.printStackTrace()}")
        }
    }

    private fun SubirInformacion(){
        val Id = reference.push().key!!
        val Imagen = UrlFoto.text.toString()
        val Nombre = EditTextNombre.text.toString()
        val Telefono = EditTextNumeroTelefonico.text.toString()
        val Ciudad = TextCiudad.text.toString()
        val Asignatura = TextAsignatura.text.toString()
        val Informacion = EditTextInformacion.text.toString()

        val map: MutableMap<String, Any> = HashMap()
        map["Id"] = Id
        map["Url"] = Imagen
        map["Nombre"] = Nombre
        map["Telefono"] = Telefono
        map["Ciudad"] = Ciudad
        map["Asignatura"] = Asignatura
        map["Informacion"] = Informacion

        if(Asignatura.equals("Matematicas")){
            reference1 = FirebaseDatabase.getInstance().getReference().child("Matematicas")
            reference1.child(Id).setValue(map)
        }else if(Asignatura.equals("Idiomas")){
            reference1 = FirebaseDatabase.getInstance().getReference().child("Idiomas")
            reference1.child(Id).setValue(map)
        }else if(Asignatura.equals("Deportes")){
            reference1 = FirebaseDatabase.getInstance().getReference().child("Deportes")
            reference1.child(Id).setValue(map)
        }else if(Asignatura.equals("Arte")){
            reference1 = FirebaseDatabase.getInstance().getReference().child("Arte")
            reference1.child(Id).setValue(map)
        }else if(Asignatura.equals("Geografia")){
            reference1 = FirebaseDatabase.getInstance().getReference().child("Geografia")
            reference1.child(Id).setValue(map)
        }

        if(Imagen != "" && Nombre != "" && Telefono != "" && Ciudad != "" && Informacion != ""){
            reference.child(Id).setValue(map).addOnCompleteListener {
                Toast.makeText(this, "Se ha creado un nuevo anuncio", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Ingrese los datos requeridos para continuar", Toast.LENGTH_SHORT).show()
        }
    }
}