package com.tsdc_vynils_app.app.ui.newAlbum

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.ActivityNewAlbumBinding
import com.tsdc_vynils_app.app.databinding.FragmentHomeBinding
import com.tsdc_vynils_app.app.viewModels.AlbumDetailsViewModel
import com.tsdc_vynils_app.app.viewModels.MusicianViewModel
import com.tsdc_vynils_app.app.viewModels.NewAlbumViewModel
import kotlinx.coroutines.launch
import java.net.URL
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


        //_binding.viewModel

        val viewModel = ViewModelProvider(this, NewAlbumViewModel.Factory(this.application)).get(NewAlbumViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

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


        genresSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                viewModel.onGenreSelected(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                viewModel.noGenreSelected()
            }
        }

        genresAdapter.notifyDataSetChanged()

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

        recordLabelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                viewModel.onRecordLabelSelected(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                viewModel.noRecordLabelSelected()
            }
        }

        recordLabelAdapter.notifyDataSetChanged()

        //buttons
        val cancelButton=binding.cancelAlbumButton
        cancelButton.setOnClickListener()
        {
            this.onSupportNavigateUp()
        }

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()

            }
        })

        val saveButton=binding.saveAlbumButton

        saveButton.setOnClickListener(){
            lifecycleScope.launch {
                try {
                    if(viewModel.saveAlbum()){
                        onBackPressed()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

        binding.editTextDateRelease.setEnabled(false);

        //load image from url
        val editTextCover=binding.coverUrlText
        editTextCover.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                loadImageFromUrl(s.toString())
            }
        })

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

    private fun loadImageFromUrl(url: String) {
        try {
            URL(url)
            Picasso.get().load(url).into(binding.albumCover)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}