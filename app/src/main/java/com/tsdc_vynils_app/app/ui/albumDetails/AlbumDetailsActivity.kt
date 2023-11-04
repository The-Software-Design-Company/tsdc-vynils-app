package com.tsdc_vynils_app.app.ui.albumDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsdc_vynils_app.app.databinding.ActivityMainBinding
import com.tsdc_vynils_app.app.ui.components.carousel.adapter.CarouselAdapter
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.ActivityAlbumDetailsBinding


class AlbumDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetailsBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_album_details)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val titleTextView = toolbar.findViewById<TextView>(R.id.toolbar_title)
        titleTextView.text = getResources().getString(R.string.title_activity_album_details)
        val iconImageView = findViewById<AppCompatImageView>(R.id.toolbar_icon)
        iconImageView.setImageDrawable(getDrawable(R.drawable.arrow_left))
        iconImageView.setOnClickListener {
            this.onSupportNavigateUp()
        }
        val titleAlbum = this.findViewById<TextView>(R.id.album_details_title)
        titleAlbum.text = "I Rise"

        val artistAlbum = this.findViewById<TextView>(R.id.album_artist_name)
        artistAlbum.text = "Etana"

        val albumReleaseDate = this.findViewById<TextView>(R.id.album_release_date)
        albumReleaseDate.text = "Octubre 28 de 2018"

        val recordLabelAlbum = this.findViewById<TextView>(R.id.album_record_label)
        recordLabelAlbum.text = "VP Music Group, Inc"

        val albumGenre = this.findViewById<TextView>(R.id.album_genre)
        albumGenre.text = "Octubre 28 de 2018"

        val albumDescription = this.findViewById<TextView>(R.id.album_description)
        albumDescription.text = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984."

        val images = listOf(R.drawable.and_justice_for_all, R.drawable.and_justice_for_all, R.drawable.and_justice_for_all)
        val adapter = CarouselAdapter(images)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Handle the Up button click event
        return true
    }


}