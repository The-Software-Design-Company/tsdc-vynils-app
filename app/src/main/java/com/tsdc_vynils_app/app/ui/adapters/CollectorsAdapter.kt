package com.tsdc_vynils_app.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.models.Collector




class CollectorsAdapter(private val listaElementos: List<Collector>) :
    RecyclerView.Adapter<CollectorsAdapter.CollectorViewHolder>() {

    class CollectorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.imageCollectorItem)
        val texto: TextView = itemView.findViewById(R.id.textCollectorItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.collector_item, parent, false)
        return CollectorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        val elementoActual = listaElementos[position]
        holder.imagen.setImageResource(elementoActual.imagenResId)
        holder.texto.text = elementoActual.name
    }

    override fun getItemCount(): Int {
        return listaElementos.size
    }
}
