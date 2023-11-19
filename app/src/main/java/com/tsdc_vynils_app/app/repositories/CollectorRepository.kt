package com.tsdc_vynils_app.app.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.models.Collector
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter

class CollectorRepository(val application: Application) {

    suspend fun refreshData(): List<Collector>{
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }
}