package com.furkan.beinConnectMovies.ui.movies.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkan.beinConnectMovies.data.remote.model.GenreModel
import com.furkan.beinConnectMovies.data.remote.model.GenreObject
import com.furkan.beinConnectMovies.data.remote.repository.GenreRepository
import kotlinx.coroutines.launch

class GenresViewModel : ViewModel() {

    private val _getGenre = MutableLiveData<GenreModel>()
    val getGenre: LiveData<GenreModel>
        get() = _getGenre

    fun getGenreList() {
        viewModelScope.launch {
            if (GenreRepository().getMovieGenres().data != null) {
                _getGenre.value = GenreRepository().getMovieGenres().data as GenreModel
            }
        }
    }

}