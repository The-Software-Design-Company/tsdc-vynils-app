package com.tsdc_vynils_app.app.network


import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.tsdc_vynils_app.app.models.Album
import org.json.JSONArray
import com.google.gson.Gson
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.models.Collector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import com.tsdc_vynils_app.app.BuildConfig as Config
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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


    suspend fun getAlbums()= suspendCoroutine<List<Album>>{ cont->
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                var list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {//inicializado como variable de retorno
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(id = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"), comments = emptyList(), performers = emptyList(), tracks = emptyList() ))
                }
                list= list.sortedBy { it.name }.toMutableList()
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }


    suspend fun getAlbum (albumId: Int)= suspendCoroutine<Album>{ cont->
        requestQueue.add(
            getRequest("albums/${albumId}",
                Response.Listener<String> { response ->
                    val album = Gson().fromJson(response, Album::class.java)
                    cont.resume(album)
                },
                Response.ErrorListener {
                    cont.resumeWithException(it)
                }))
    }



    suspend fun getMusicians()= suspendCoroutine<List<Musician>> {  cont->
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                var list = mutableListOf<Musician>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    val dateString = item.getString("birthDate")
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    val birthDateMusician: Date = inputFormat.parse(dateString)

                    list.add(i, Musician(
                        id = item.getInt("id"),
                        name = item.getString("name"), image = item.getString("image"),
                        description = item.getString("description"), birthDate = birthDateMusician, albums = emptyList(),
                        performerPrizes =emptyList(),
                        imagenResId = 0))
                }

                list= list.sortedBy { it.name }.toMutableList()
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getCollectors()= suspendCoroutine<List<Collector>> { cont->
        requestQueue.add(getRequest("collectors",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                var list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Collector(id = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email"), profilePicture = "", comments = emptyList(), favoritePerformers = emptyList(), collectorAlbums = emptyList() ))
                }
                list= list.sortedBy { it.name }.toMutableList()
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))

    }

    suspend fun getBandsToArtists()= suspendCoroutine<List<Musician>> {  cont->
        requestQueue.add(getRequest("bands",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                var list = mutableListOf<Musician>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    val dateString = item.getString("creationDate")
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    val birthDateMusician: Date = inputFormat.parse(dateString)

                    list.add(i, Musician(
                        id = item.getInt("id"),
                        name = item.getString("name"), image = item.getString("image"),
                        description = item.getString("description"), birthDate = birthDateMusician, albums = emptyList(),
                        performerPrizes =emptyList(),
                        imagenResId = 0))
                }

                list= list.sortedBy { it.name }.toMutableList()
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }


    suspend fun postNewAlbum(
        body: JSONObject
    ): JSONObject = withContext(Dispatchers.IO) {
        return@withContext suspendCoroutine { continuation ->
            requestQueue.add(
                postRequest(
                    "albums",
                    body,
                    Response.Listener { response ->
                        continuation.resume(response)
                    },
                    Response.ErrorListener { error ->
                        continuation.resumeWithException(error)
                    }
                )
            )
        }
    }

    suspend fun postAssociateTrackToAlbum(albumId: Int, body: JSONObject): JSONObject = withContext(Dispatchers.IO) {
        return@withContext suspendCoroutine { continuation ->  requestQueue.add(
            postRequest(
                "albums/${albumId}/tracks",
                body,
                Response.Listener {
                        response -> continuation.resume(response)
                },
                Response.ErrorListener {  error->
                    continuation.resumeWithException(error)
                }
            )
        )}
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

    private fun postRequest(path: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ): JsonObjectRequest {
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
}