package com.tsdc_vynils_app.app.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.ui.artistDetails.ArtistDetailsActivity
import com.tsdc_vynils_app.app.viewModels.ArtistDetailViewModel


class MusiciansAdapter ():RecyclerView.Adapter<MusiciansAdapter.MusicianViewHolder>(), Filterable {

    var elementList: List<Musician> = emptyList()
        set(value){
            field=value
            notifyDataSetChanged()
        }

    var elementListCopy: List<Musician> = elementList



    private var filteredData: List<Musician> =elementList

    class MusicianViewHolder(itemView: View , private val onItemClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageArtistItem)
        val text: TextView = itemView.findViewById(R.id.textArtistItem)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianViewHolder {
        elementListCopy=elementList.toList()
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.artist_item, parent, false)

        val onItemClickListener: (Int) -> Unit = { position ->
            val musician = elementList[position]
            val intent = Intent(itemView.context, ArtistDetailsActivity::class.java)
            intent.putExtra("musician", musician)

            val artistDetailViewModel = ViewModelProvider(itemView.context as AppCompatActivity)
                .get(ArtistDetailViewModel::class.java)
            artistDetailViewModel.setMusician(musician)


            itemView.context.startActivity(intent)
        }

        return MusicianViewHolder(itemView,onItemClickListener)
    }




    override fun onBindViewHolder(holder: MusicianViewHolder, position: Int) {
        val currentElement = elementList[position]
        Picasso.get()
            .load(currentElement.image)
            .into(holder.image)
        holder.text.text = currentElement.name
    }

    override fun getItemCount(): Int {
        return elementList.size
    }

    override fun getFilter(): Filter {
        elementList=elementListCopy.toList()
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val filterResults = FilterResults()
                val searchText = constraint?.toString()?.toLowerCase()


                filteredData = if (searchText.isNullOrBlank()) {
                    elementList
                } else {
                    elementList.filter { item ->
                        item.name.toLowerCase().contains(searchText)
                    }
                }

                filterResults.values = filteredData
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                elementList = results?.values as? List<Musician> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

    fun sortByName(asc: Boolean) {
        elementList = if (asc) {
            elementList.sortedBy { it.name }
        } else {
            elementList.sortedByDescending { it.name }
        }
        notifyDataSetChanged()
    }
}

