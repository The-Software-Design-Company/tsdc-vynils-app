package com.tsdc_vynils_app.app.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.tsdc_vynils_app.app.models.Collector
import com.tsdc_vynils_app.app.network.CacheManagerCollectorDetails
import com.tsdc_vynils_app.app.network.NetworkServiceAdapter

class CollectorRepository(val application: Application) {

    suspend fun refreshData(): List<Collector>{
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }

    suspend fun refreshDataById(collectorId: Int):Collector{
        var potentialCollector= CacheManagerCollectorDetails.getInstance(application.applicationContext).getCollector(collectorId)
        if(potentialCollector==null){
            //consultar de la red
            var collector=NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
            return collector
        }
        else
            return potentialCollector

    }
}