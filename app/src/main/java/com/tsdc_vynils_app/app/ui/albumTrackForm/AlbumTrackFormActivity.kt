package com.tsdc_vynils_app.app.ui.albumTrackForm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.ActivityAlbumTrackFormBinding
import com.tsdc_vynils_app.app.databinding.ActivityNewAlbumBinding
import com.tsdc_vynils_app.app.viewModels.AlbumTrackViewModel
import com.tsdc_vynils_app.app.viewModels.NewAlbumViewModel
import kotlinx.coroutines.launch

class AlbumTrackFormActivity : AppCompatActivity() {
    private var _binding: ActivityAlbumTrackFormBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_track_form)
        _binding = ActivityAlbumTrackFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(this, AlbumTrackViewModel.Factory(this.application)).get(
            AlbumTrackViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

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
        val albumId = intent.getIntExtra("albumId", 0)
        val titleTextView = toolbar.findViewById<TextView>(R.id.toolbar_title)
        titleTextView.text = "${artist} ${album}"

        val cancelButton = findViewById<Button>(R.id.btnCancelar)
        cancelButton.setOnClickListener {
            this.onSupportNavigateUp()
        }

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()

            }
        })

        if (albumId > 0) {
            val saveButton=binding.btnGuardar

            saveButton.setOnClickListener(){
                lifecycleScope.launch {
                    try {
                        if(viewModel.saveTrack(albumId)){
                            onBackPressedDispatcher.onBackPressed()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        finish()
        return true
    }


}