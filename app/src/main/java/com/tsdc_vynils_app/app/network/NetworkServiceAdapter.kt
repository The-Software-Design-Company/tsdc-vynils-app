package com.tsdc_vynils_app.app.network


import android.content.Context
import android.util.Log
import com.android.volley.BuildConfig
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.tsdc_vynils_app.app.models.Album
import org.json.JSONArray
import org.json.JSONObject
import com.google.gson.Gson
import com.tsdc_vynils_app.app.models.Collector
import com.tsdc_vynils_app.app.BuildConfig as Config

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= Config.BASE_API_URL
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(id = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"), comments = emptyList(), performers = emptyList(), tracks = emptyList() ))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    fun getAlbum(albumId: Int, onComplete: (response: Album)->Unit, onError: (error: VolleyError)->Unit) {
        requestQueue.add(
            getRequest("albums/${albumId}",
                Response.Listener<String> { response ->
                    val album = Gson().fromJson(response, Album::class.java)
                    onComplete(album)
                },
                Response.ErrorListener {
                    onError(it)
                }
                )
        )
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

    fun getCollectors(onComplete:(resp:List<Collector>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("collectors",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Collector(id = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email"), imagenResId = 0, comments = emptyList(), favoritePerformers = emptyList(), collectorAlbums = emptyList() ))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }
}