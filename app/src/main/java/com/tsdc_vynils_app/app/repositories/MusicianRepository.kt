
package com.tsdc_vynils_app.app.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter

class MusicianRepository (val application: Application){
    suspend fun refreshData(): List<Musician>{
        return NetworkServiceAdapter.getInstance(application).getMusicians()
    }


}