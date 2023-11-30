package com.tsdc_vynils_app.app.ui.albumTrackForm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.tsdc_vynils_app.app.R

class AlbumTrackFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_track_form)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val iconImageView = findViewById<AppCompatImageView>(R.id.toolbar_icon)
        iconImageView.setImageDrawable(getDrawable(R.drawable.arrow_left))
        iconImageView.setOnClickListener {
            this.onSupportNavigateUp()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Handle the Up button click event
        this.onDestroy()
        return true
    }

}