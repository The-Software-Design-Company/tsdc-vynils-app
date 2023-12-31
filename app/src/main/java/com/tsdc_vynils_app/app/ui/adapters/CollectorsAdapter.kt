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
import com.tsdc_vynils_app.app.models.Collector
import com.tsdc_vynils_app.app.ui.artistDetails.ArtistDetailsActivity
import com.tsdc_vynils_app.app.ui.collectorDetails.CollectorDetailsActivity
import com.tsdc_vynils_app.app.viewModels.ArtistDetailViewModel
import com.tsdc_vynils_app.app.viewModels.CollectorDetailsViewModel


class CollectorsAdapter ():RecyclerView.Adapter<CollectorsAdapter.CollectorViewHolder>(), Filterable {

    var elementList: List<Collector> = emptyList()
        set(value){
            field=value
            notifyDataSetChanged()
        }

    var elementListCopy: List<Collector> = emptyList()



    private var filteredData: List<Collector> =elementList





    class CollectorViewHolder(itemView: View, private val onItemClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageCollectorItem)
        val text: TextView = itemView.findViewById(R.id.textCollectorItem)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        elementListCopy=elementList.toList()
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.collector_item, parent, false)

        val onItemClickListener: (Int) -> Unit = { position ->
            val collector = elementList[position]
            val intent = Intent(itemView.context, CollectorDetailsActivity::class.java)
            intent.putExtra("collector", collector)


            val collectorsDetailViewModel = ViewModelProvider(itemView.context as AppCompatActivity)
                .get(CollectorDetailsViewModel::class.java)
            collectorsDetailViewModel.setCollector(collector)


            itemView.context.startActivity(intent)
        }

        return CollectorsAdapter.CollectorViewHolder(itemView, onItemClickListener)
    }




    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        val currentElement = elementList[position]
        holder.image.setImageResource(R.drawable.default_avatar)
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
                    elementListCopy
                } else {
                    elementList.filter { item ->
                        item.name.toLowerCase().contains(searchText)
                    }
                }

                filterResults.values = filteredData
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                elementList = results?.values as? List<Collector> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}