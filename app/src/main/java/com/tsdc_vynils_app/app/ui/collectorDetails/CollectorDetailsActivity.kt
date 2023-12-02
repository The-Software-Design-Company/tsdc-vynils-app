package com.tsdc_vynils_app.app.ui.collectorDetails

import androidx.appcompat.app.AppCompatActivity
import com.tsdc_vynils_app.app.databinding.ActivityCollectorDetailsBinding
import com.tsdc_vynils_app.app.viewModels.CollectorDetailsViewModel


class CollectorDetailsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCollectorDetailsBinding
    private var _bindingData: ActivityCollectorDetailsBinding? = null

    private val bindingData get() = _bindingData!!
    private lateinit var albumDetailsViewModel: CollectorDetailsViewModel
}