package com.tsdc_vynils_app.app.ui.albumDetails

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.tsdc_vynils_app.app.databinding.ActivityMainBinding
import com.tsdc_vynils_app.app.ui.components.carousel.adapter.CarouselAdapter
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.ActivityAlbumDetailsBinding
import com.tsdc_vynils_app.app.databinding.FragmentDashboardBinding
import com.tsdc_vynils_app.app.databinding.FragmentHomeBinding
import com.tsdc_vynils_app.app.ui.albumTrackForm.AlbumTrackFormActivity
import com.tsdc_vynils_app.app.viewModels.AlbumDetailsViewModel
import com.tsdc_vynils_app.app.viewModels.HomeViewModel
import com.tsdc_vynils_app.app.utils.DateUtils


class AlbumDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumDetailsBinding
    private var _bindingData: ActivityAlbumDetailsBinding? = null

    private val bindingData get() = _bindingData!!
    private lateinit var albumDetailsViewModel: AlbumDetailsViewModel


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

        val bundle: Bundle? = intent.extras

        if (bundle != null) {
            val albumId = bundle.getInt("albumId", 0)
            val viewModel = ViewModelProvider(this, AlbumDetailsViewModel.Factory(this.application, albumId)).get(AlbumDetailsViewModel::class.java)
            val titleAlbum = this.findViewById<TextView>(R.id.album_details_title)
            val albumReleaseDate = this.findViewById<TextView>(R.id.album_release_date)
            val recordLabelAlbum = this.findViewById<TextView>(R.id.album_record_label)
            val albumGenre = this.findViewById<TextView>(R.id.album_genre)
            val albumDescription = this.findViewById<TextView>(R.id.album_description)
            val artistAlbum = this.findViewById<TextView>(R.id.album_artist_name)
            val intent = Intent(this, AlbumTrackFormActivity::class.java)
            viewModel.album.observe(this, Observer { data ->
                data.apply {
                    titleAlbum.text = data.name
                    val dateFormat = DateUtils.CustomDateFormat(data.releaseDate)
                    albumReleaseDate.text = dateFormat
                    recordLabelAlbum.text = data.recordLabel
                    albumGenre.text = data.genre
                    albumDescription.text = data.description
                    val imageUrl = data.cover
                    val coverView = findViewById<ImageView>(R.id.album_cover)
                    if (imageUrl!="")
                        Picasso.get()
                            .load(imageUrl)
                            .into(coverView)
                    else
                        coverView.setImageDrawable( getDrawable(R.drawable.default_image))
                    val artist = data.performers.joinToString(",") { "${it.name} " }
                    artistAlbum.text = artist

                    val associateTrackText = binding.associateTrack
                    associateTrackText.setOnClickListener {
                        val album = data.name
                        val performer = if (data.performers.size > 0) data.performers[0].name else ""
                        intent.putExtra("artist", performer)
                        intent.putExtra("album", album)
                        startActivity(intent)
                    }

                }
            })

            val titleTextView = toolbar.findViewById<TextView>(R.id.toolbar_title)
            titleTextView.text = getResources().getString(R.string.title_activity_album_details)
            val iconImageView = findViewById<AppCompatImageView>(R.id.toolbar_icon)
            iconImageView.setImageDrawable(getDrawable(R.drawable.arrow_left))
            iconImageView.setOnClickListener {
                this.onSupportNavigateUp()
            }


            val images = listOf(R.drawable.default_image, R.drawable.default_image, R.drawable.default_image)
            val adapter = CarouselAdapter(images,"Álbum")
            binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerView.adapter = adapter
        }
        else {
            Toast.makeText(this, "Argument is missing", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "Argument is missing")
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _bindingData = null
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Handle the Up button click event
        return true
    }


}