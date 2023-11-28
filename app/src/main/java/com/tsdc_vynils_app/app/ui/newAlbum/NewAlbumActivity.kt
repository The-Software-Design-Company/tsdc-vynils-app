package com.tsdc_vynils_app.app.ui.newAlbum

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.ActivityNewAlbumBinding
import com.tsdc_vynils_app.app.databinding.FragmentHomeBinding
import com.tsdc_vynils_app.app.ui.albumTrackForm.AlbumTrackFormActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class newAlbumActivity : AppCompatActivity() {


    private var _binding: ActivityNewAlbumBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_album)

        _binding = ActivityNewAlbumBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val toolbar = binding.toolbar
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bundle: Bundle? = intent.extras

        //toolbar setup
        val titleTextView = binding.toolbar.toolbarTitle
        titleTextView.text = getResources().getString(R.string.title_new_album)
        val iconImageView = binding.toolbar.toolbarIcon
        iconImageView.setImageDrawable(getDrawable(R.drawable.arrow_left))
        iconImageView.setOnClickListener {
            this.onSupportNavigateUp()
        }

        //setup spinners
        val genresList = resources.getStringArray(R.array.musical_genres)
        val genresSpinner=binding.spinnerGenreList
        val genresAdapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            genresList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(ContextCompat.getColor(this@newAlbumActivity, R.color.colorSpinnerText))
                return view
            }
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(ContextCompat.getColor(this@newAlbumActivity, R.color.colorSpinnerText))
                return view
            }
        }

        genresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genresSpinner.adapter=genresAdapter

        val recordLabelList = resources.getStringArray(R.array.musical_record_labels)
        val recordLabelSpinner=binding.spinnerRecordLabelList
        val recordLabelAdapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            recordLabelList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(ContextCompat.getColor(this@newAlbumActivity, R.color.colorSpinnerText))
                return view
            }
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(ContextCompat.getColor(this@newAlbumActivity, R.color.colorSpinnerText))
                return view
            }
        }
        recordLabelSpinner.adapter=recordLabelAdapter

        val associateTrackText = findViewById<TextView>(R.id.associateTrack)
        associateTrackText.setOnClickListener {
            val intent = Intent(this, AlbumTrackFormActivity::class.java)
            startActivity(intent)
        }
     }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Handle the Up button click event
        return true
    }


    fun showDatePickerDialog(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val releaseDateText=binding.editTextDateRelease
        

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)


                releaseDateText.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }


}