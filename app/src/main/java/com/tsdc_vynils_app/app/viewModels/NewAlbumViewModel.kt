package com.tsdc_vynils_app.app.viewModels

import androidx.lifecycle.AndroidViewModel
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tsdc_vynils_app.app.R
import com.tsdc_vynils_app.app.models.Album
import com.tsdc_vynils_app.app.repositories.AlbumRepository
import com.tsdc_vynils_app.app.repositories.MusicianRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewAlbumViewModel(application: Application) : AndroidViewModel(application) {

    private val albumRepository = AlbumRepository(application)
    val album = MutableLiveData<Album>()
    private val _errorMessage = MutableLiveData<String>()
    val listGenres = MutableLiveData<List<String>>()
    var genreSelected = MutableLiveData<Int>()


    val listRecordLabels = MutableLiveData<List<String>>()
    var recordLabelSelected = MutableLiveData<Int>()

    init {
        album.value = Album()

        viewModelScope.launch(Dispatchers.IO) {
            val genresArray = getApplication<Application>().resources.getStringArray(R.array.musical_genres)
            listGenres.postValue(genresArray.toList())

            val recordLabelsArray=getApplication<Application>().resources.getStringArray(R.array.musical_record_labels)
            listRecordLabels.postValue(recordLabelsArray.toList())
        }
    }




    public val errorMessage: LiveData<String>
        get() = _errorMessage

    fun setErrorMessage(errorMessage: String) {
        _errorMessage.value = errorMessage
    }

    fun onGenreSelected(position: Int) {

        if(position==0) {
            album.value?.genre = ""
        }
        else{
            genreSelected.value= position
            album.value?.genre = listGenres.value?.get(genreSelected.value!!).toString()
        }

    }
    fun noGenreSelected(){
        album.value?.genre=""
    }

    fun onRecordLabelSelected(position: Int) {
        if(position==0) {
            album.value?.recordLabel = ""
        }
        else {
            recordLabelSelected.value = position
            album.value?.recordLabel =
                listRecordLabels.value?.get(recordLabelSelected.value!!).toString()
        }
    }
    fun noRecordLabelSelected(){
        album.value?.recordLabel=""
    }



    private fun validations(): Boolean {
        val currentAlbum = album.value
        if (currentAlbum?.name.isNullOrBlank()) {
            _errorMessage.value = "El nombre del álbum es requerido"
            return false
        }
        if (currentAlbum?.releaseDate.isNullOrBlank()) {
            _errorMessage.value = "La fecha de lanzamiento es requerida"
            return false
        }
        if (currentAlbum?.description.isNullOrBlank()) {
            _errorMessage.value = "La descripción requerida"
            return false
        }
        if (currentAlbum?.genre.isNullOrBlank()) {
            _errorMessage.value = "El género es requerido"
            return false
        }
        if (currentAlbum?.recordLabel.isNullOrBlank()) {
            _errorMessage.value = "La disquera es requerida"
            return false
        }
        return true
    }

    suspend fun saveAlbum() {
        val currentAlbum = album.value
        if (validations()) {
            if (currentAlbum != null) {
                try {
                    albumRepository.postNewAlbum(currentAlbum)
                    _errorMessage.value ="Álbum almacenado con éxito "
                }
                catch(ex:Exception){
                    _errorMessage.value ="No fue posible almacenar el álbum, intentelo más tarde "+ ex.message
                }
            }

        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewAlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}