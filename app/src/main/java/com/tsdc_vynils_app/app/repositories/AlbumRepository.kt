package com.tsdc_vynils_app.app.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.network.CacheManagerAlbumDetails
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

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

    suspend fun postNewAlbum(album:Album){
        val sourceFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val finalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        finalFormat.timeZone = TimeZone.getTimeZone("GMT-05:00")

        val date = sourceFormat.parse(album.releaseDate)

        val releaseDate=finalFormat.format(date)

        val albumJson = JSONObject()
        albumJson.put("name", album.name)
        albumJson.put("cover", album.cover)
        albumJson.put("releaseDate", releaseDate)
        albumJson.put("description", album.description)
        albumJson.put("genre", album.genre)
        albumJson.put("recordLabel", album.recordLabel)
        NetworkServiceAdapter.getInstance(application).postNewAlbum(albumJson)
    }


}