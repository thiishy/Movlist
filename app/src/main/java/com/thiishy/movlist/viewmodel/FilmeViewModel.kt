package com.thiishy.movlist.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thiishy.movlist.data.FilmeRepository
import com.thiishy.movlist.model.AppDatabase
import com.thiishy.movlist.model.Filme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FilmeRepository(AppDatabase.Companion.getDatabase(application))

    val filmes: LiveData<List<Filme>> = repository.getAllMovies().asLiveData()

    private val _uriPoster = MutableLiveData<Uri?>()
    val uriPoster: LiveData<Uri?> get() = _uriPoster

    fun setUriPoster(uri: Uri?) {
        _uriPoster.value = uri
    }

    fun alterWatchedStatusByUid(filme: Filme, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.alterWatchedStatusByUid(filme.uid, isChecked)
        }
    }

    fun insertMovie(filme: Filme) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovie(filme)
        }
    }

    fun removeMovie(filme: Filme) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeMovie(filme)
        }
    }
}