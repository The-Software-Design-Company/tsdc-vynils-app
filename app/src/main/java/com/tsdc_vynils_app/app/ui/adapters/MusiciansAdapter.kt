package com.tsdc_vynils_app.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.models.Musician

class MusiciansAdapter(private val elementList: List<Musician>) :
    RecyclerView.Adapter<MusiciansAdapter.MusicianViewHolder>() {

    class MusicianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageArtistItem)
        val text: TextView = itemView.findViewById(R.id.textArtistItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.artist_item, parent, false)
        return MusicianViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MusicianViewHolder, position: Int) {
        val currentElement = elementList[position]
        holder.image.setImageResource(currentElement.imagenResId)
        holder.text.text = currentElement.name
    }

    override fun getItemCount(): Int {
        return elementList.size
    }
}
