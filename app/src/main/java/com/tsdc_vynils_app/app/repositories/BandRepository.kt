package com.tsdc_vynils_app.app.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter

class BandRepository (val application: Application){

    suspend fun refreshDataForMusician(): List<Musician>{
        return NetworkServiceAdapter.getInstance(application).getBandsToArtists()
    }


}