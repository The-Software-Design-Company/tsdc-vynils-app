package com.tsdc_vynils_app.app.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tsdc_vynils_app.app.models.Musician
import com.tsdc_vynils_app.app.repositories.BandRepository
import com.tsdc_vynils_app.app.repositories.MusicianRepository

class MusicianViewModel(application: Application) :  AndroidViewModel(application) {

    private val musicianRepository = MusicianRepository(application)
    private val bandRepository = BandRepository(application)

    private val _musicians = MutableLiveData<List<Musician>>()


    val musicians: LiveData<List<Musician>>
        get() = _musicians

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {

        musicianRepository.refreshData({ musicians ->
            bandRepository.refreshDataForMusician({ bandResults ->
                val combinedList = mutableListOf<Musician>()
                combinedList.addAll(musicians)
                combinedList.addAll(bandResults)
                _musicians.postValue(combinedList.sortedBy { it.name })
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            }, { error ->
                _eventNetworkError.value = true
            })

        }, { error ->
            _eventNetworkError.value = true
        })
    }



    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MusicianViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MusicianViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}