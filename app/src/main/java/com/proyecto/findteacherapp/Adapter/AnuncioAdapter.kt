package com.proyecto.findteacherapp.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.findteacherapp.DetallesAnuncio
import com.proyecto.findteacherapp.Model.AnuncioModel
import com.proyecto.findteacherapp.R
import com.squareup.picasso.Picasso

class AnuncioAdapter (
    private val SitioLista : ArrayList<AnuncioModel>)
    : RecyclerView.Adapter<AnuncioAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.perfil_fragment, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val lista = SitioLista[position]
        holder.Id.text = lista.Id
        holder.Asignatura.text = lista.Asignatura
        holder.Ciudad.text = lista.Ciudad
        holder.Informacion.text = lista.Informacion
        holder.Nombre.text = lista.Nombre
        holder.Telefono.text = lista.Telefono
        Picasso.get().load(lista.Url).into(holder.Url)

        holder.Seleccionar.setOnClickListener{
            val intent = Intent(holder.Seleccionar.context, DetallesAnuncio::class.java)
            intent.putExtra("Id", lista.Id)
            intent.putExtra("Nombre", lista.Nombre)
            intent.putExtra("Telefono", lista.Telefono)
            intent.putExtra("Informacion", lista.Informacion)
            intent.putExtra("Ciudad", lista.Ciudad)
            intent.putExtra("Asignatura", lista.Asignatura)
            intent.putExtra("Url", lista.Url)
            holder.Seleccionar.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return SitioLista.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val Asignatura : TextView = itemView.findViewById(R.id.TxtAsignatura)
        val Ciudad : TextView = itemView.findViewById(R.id.TxtUbicacionDocente)
        val Id : TextView = itemView.findViewById(R.id.TxtId)
        val Informacion : TextView = itemView.findViewById(R.id.TxtInformacion)
        val Nombre : TextView = itemView.findViewById(R.id.TxtNombreDocente)
        val Telefono : TextView = itemView.findViewById(R.id.TxtTelefonoDocente)
        val Url : ImageView = itemView.findViewById(R.id.ImagenPerfil)
        val Seleccionar : LinearLayout = itemView.findViewById(R.id.SeleccionPerfil)
    }
}