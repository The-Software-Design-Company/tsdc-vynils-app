package com.tsdc_vynils_app.app.ui.collectorDetails

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.ActivityArtistDetailsBinding
import com.tsdc_vynils_app.app.databinding.ActivityCollectorDetailsBinding
import com.tsdc_vynils_app.app.models.Collector
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.ui.components.carousel.adapter.CarouselAdapter
import com.tsdc_vynils_app.app.viewModels.ArtistDetailViewModel
import com.tsdc_vynils_app.app.viewModels.CollectorDetailsViewModel


class CollectorDetailsActivity: AppCompatActivity() {

    private lateinit var viewModel: CollectorDetailsViewModel
    private lateinit var binding: ActivityCollectorDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectorDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titleTextView = toolbar.findViewById<TextView>(R.id.toolbar_title)
        titleTextView.text = getResources().getString(R.string.collectors_title_description)

        val iconImageView = findViewById<AppCompatImageView>(R.id.toolbar_icon)
        iconImageView.setImageDrawable(getDrawable(R.drawable.arrow_left))
        iconImageView.setOnClickListener {
            this.onSupportNavigateUp()
        }

        val collectorName=this.findViewById<TextView>(R.id.collector_detail_name)
        val collectorPhone=this.findViewById<TextView>(R.id.collector_detail_phone)
        val collectorEmail=this.findViewById<TextView>(R.id.collector_detail_email)


        val receivedIntent = intent
        val receivedObject = receivedIntent.getSerializableExtra("collector") as Collector?

        if (receivedObject != null) {
            collectorName.text=receivedObject.name
            collectorPhone.text=receivedObject.telephone
            collectorEmail.text=receivedObject.email
        }


        val images = listOf(R.drawable.default_avatar, R.drawable.default_avatar, R.drawable.default_avatar)
        val adapter = CarouselAdapter(images,"Artista")
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Handle the Up button click event
        return true
    }
}