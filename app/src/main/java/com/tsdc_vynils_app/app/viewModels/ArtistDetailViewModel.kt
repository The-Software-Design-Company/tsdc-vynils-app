package com.tsdc_vynils_app.app.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tsdc_vynils_app.app.models.Musician

class ArtistDetailViewModel() : ViewModel() {
    private val _musician = MutableLiveData<Musician>()

    public val musician: LiveData<Musician>
        get() = _musician

    fun setMusician(musician: Musician) {
        _musician.value = musician
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistDetailViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}