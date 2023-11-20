package com.tsdc_vynils_app.app.network

import android.content.Context
import com.tsdc_vynils_app.app.models.Album


class CacheManagerAlbumDetails(context: Context) {
    companion object{
        var instance: CacheManagerAlbumDetails? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManagerAlbumDetails(context).also {
                    instance = it
                }
            }
    }
    private var albums: HashMap<Int, Album> = hashMapOf()
    fun addAlbum(albumId: Int, album: Album){
        if (!albums.containsKey(albumId)){
            albums[albumId] = album
        }
    }
    fun getAlbum(albumId: Int) : Album? {
        return if (albums.containsKey(albumId)) albums[albumId]!! else null
    }
}