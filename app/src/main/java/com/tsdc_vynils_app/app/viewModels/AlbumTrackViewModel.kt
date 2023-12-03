package com.tsdc_vynils_app.app.viewModels

import android.app.Application
import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.models.Track
import com.tsdc_vynils_app.app.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class AlbumTrackViewModel(application: Application) : AndroidViewModel(application)  {
    private val albumRepository = AlbumRepository(application)
    var track = MutableLiveData<Track>()
    private val _errorMessage = MutableLiveData<String>()


    init {
        track.value = Track()
    }

    public val errorMessage: LiveData<String>
        get() = _errorMessage

    fun setErrorMessage(errorMessage: String) {
        _errorMessage.value = errorMessage
    }

    var trackName: String?
        get() = this.track.value?.name
        set(value) {
            if(value != null)
                track.value?.name = value
        }

    var trackDuration: String?
        get() = this.track.value?.duration
        set(value) {
            if(value != null)
                track.value?.duration = value
        }

    private fun validations(): Boolean {
        val currentTrack = track.value
        Log.e("data", "${currentTrack?.name} ${currentTrack?.duration}")
        if (currentTrack?.name.isNullOrBlank()) {
            _errorMessage.value = "El nombre del track es requerido"
            return false
        }
        if (currentTrack?.duration.isNullOrBlank()) {
            _errorMessage.value = "El duración del track es requerido"
            return false
        }

        return true
    }

    suspend fun saveTrack(albumId: Int):Boolean {
        val currentTrack = track.value
        if (validations()) {
            if (currentTrack != null) {
                try {
                    albumRepository.postAssociateTrackToAlbum(albumId, currentTrack)
                    _errorMessage.value ="Track asociado con éxito "
                    return true
                }
                catch(ex:Exception){
                    _errorMessage.value ="No fue posible almacenar el track, intentelo más tarde "
                    return false
                }
            }

        }
        return false
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumTrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumTrackViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}