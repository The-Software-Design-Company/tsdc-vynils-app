package com.tsdc_vynils_app.app.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter

class AlbumRepository (val application: Application){
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        },
            onError
        )
    }

    fun refreshDataById(albumId: Int, callback: (Album)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbum(albumId, {
            callback(it)
        },
            onError
        )
    }
}