package com.tsdc_vynils_app.app.ui.artistDetails

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.ActivityArtistDetailsBinding
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.viewModels.ArtistDetailViewModel

class ArtistDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: ArtistDetailViewModel
    private lateinit var binding: ActivityArtistDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityArtistDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_details)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titleTextView = toolbar.findViewById<TextView>(R.id.toolbar_title)
        titleTextView.text = getResources().getString(R.string.title_activity_artist_details)

        val iconImageView = findViewById<AppCompatImageView>(R.id.toolbar_icon)
        iconImageView.setImageDrawable(getDrawable(R.drawable.arrow_left))
        iconImageView.setOnClickListener {
            this.onSupportNavigateUp()
        }

        val artistName=this.findViewById<TextView>(R.id.caption_text_view)
        val artistDescription=this.findViewById<TextView>(R.id.artist_bio)

        val receivedIntent = intent
        val receivedObject = receivedIntent.getSerializableExtra("musician") as Musician?

        if (receivedObject != null) {
            artistName.text=receivedObject.name
            artistDescription.text=receivedObject.description
            val imageUrl = receivedObject.image
            val picView = findViewById<ImageView>(R.id.artist_photo)
            if (imageUrl!="")
                Picasso.get()
                    .load(imageUrl)
                    .into(picView)
            else
                picView.setImageDrawable( getDrawable(R.drawable.default_avatar))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Handle the Up button click event
        return true
    }
}