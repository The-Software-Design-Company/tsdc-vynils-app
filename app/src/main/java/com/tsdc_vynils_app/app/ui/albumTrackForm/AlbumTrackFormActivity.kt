package com.tsdc_vynils_app.app.ui.albumTrackForm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.tsdc_vynils_app.app.R

class AlbumTrackFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_track_form)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val iconImageView = findViewById<AppCompatImageView>(R.id.toolbar_icon)
        iconImageView.setImageDrawable(getDrawable(R.drawable.arrow_left))
        iconImageView.setOnClickListener {
            this.onSupportNavigateUp()
        }

        val album = intent.getStringExtra("album")
        val artist = intent.getStringExtra("artist")
        val titleTextView = toolbar.findViewById<TextView>(R.id.toolbar_title)
        titleTextView.text = "${artist} ${album}"

        val cancelButton = findViewById<Button>(R.id.btnCancelar)
        cancelButton.setOnClickListener {
            this.onSupportNavigateUp()
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        finish()
        return true
    }


}