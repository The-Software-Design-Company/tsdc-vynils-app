package com.tsdc_vynils_app.app.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.network.CacheManagerAlbumDetails
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<Album>{
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun refreshDataById(albumId: Int):Album{
        var potentialAlbum= CacheManagerAlbumDetails.getInstance(application.applicationContext).getAlbum(albumId)
        if(potentialAlbum==null){
            //consultar de la red
            var album=NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
            CacheManagerAlbumDetails.getInstance(application.applicationContext).addAlbum(albumId,album)
            return album
        }
        else
            return potentialAlbum

    }


}