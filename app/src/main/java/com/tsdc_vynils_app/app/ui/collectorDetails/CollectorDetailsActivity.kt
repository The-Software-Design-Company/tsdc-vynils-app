package com.tsdc_vynils_app.app.ui.collectorDetails

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.databinding.ActivityCollectorDetailsBinding
import com.tsdc_vynils_app.app.viewModels.CollectorDetailsViewModel


class CollectorDetailsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCollectorDetailsBinding
    private var _bindingData: ActivityCollectorDetailsBinding? = null

    private val bindingData get() = _bindingData!!
    private lateinit var collectorDetailsViewModel: CollectorDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectorDetailsBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_collector_details)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle != null) {
            val collectorId = bundle.getInt("collectorId", 0)
            val viewModel = ViewModelProvider(this, CollectorDetailsViewModel.Factory(this.application, collectorId)).get(
                CollectorDetailsViewModel::class.java)
            val collectorName = this.findViewById<TextView>(R.id.collector_detail_name)
            val collectorPhone = this.findViewById<TextView>(R.id.collector_detail_phone)
            val collectorEmail = this.findViewById<TextView>(R.id.collector_detail_email)

            viewModel.collector.observe(this, Observer { data ->
                data.apply {
                    collectorName.text = data.name
                    collectorEmail.text = data.email
                    collectorPhone.text = data.telephone
                }
            })

            val titleTextView = toolbar.findViewById<TextView>(R.id.collector_detail_title)
            titleTextView.text = "Detalle del Coleccionista"

        } else {
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