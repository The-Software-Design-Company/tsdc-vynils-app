package com.tsdc_vynils_app.app.network

import android.content.Context
import com.tsdc_vynils_app.app.models.Collector

class CacheManagerCollectorDetails(context: Context) {
    companion object{
        var instance: CacheManagerCollectorDetails? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManagerCollectorDetails(context).also {
                    instance = it
                }
            }
    }
    private var collectors: HashMap<Int, Collector> = hashMapOf()
    fun getCollector(collectorId: Int) : Collector? {
        return if (collectors.containsKey(collectorId)) collectors[collectorId]!! else null
    }
}