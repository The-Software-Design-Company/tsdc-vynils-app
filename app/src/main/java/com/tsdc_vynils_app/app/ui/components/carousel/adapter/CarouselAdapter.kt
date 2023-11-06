package com.tsdc_vynils_app.app.ui.components.carousel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsdc_vynils_app.app.databinding.ItemImageCarouselBinding

class CarouselAdapter(private val items: List<Int>) : RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(private val binding: ItemImageCarouselBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageResId: Int) {
            binding.imageView.setImageResource(imageResId)
        }
    }
}

