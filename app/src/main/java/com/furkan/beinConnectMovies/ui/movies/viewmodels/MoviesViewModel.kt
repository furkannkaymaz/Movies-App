package com.furkan.beinConnectMovies.ui.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkan.beinConnectMovies.data.remote.model.MoviesModel
import com.furkan.beinConnectMovies.data.remote.model.MoviesResult
import com.furkan.beinConnectMovies.data.remote.repository.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val _getMovies = MutableLiveData<MoviesModel>()
    val getMovies: LiveData<MoviesModel>
        get() = _getMovies

    private val _filterText = MutableLiveData<List<MoviesResult>>()
    val filterText: LiveData<List<MoviesResult>>
        get() = _filterText

    fun getMovieList(id: Int) {
        viewModelScope.launch {
            if (MoviesRepository().getMovies(id).data != null) {
                _getMovies.value = MoviesRepository().getMovies(id).data as MoviesModel
            }
        }
    }

    fun getFilterText(keyword: String, searchItem: List<MoviesResult>?) {

        viewModelScope.launch {

            val filter = searchItem?.filter { it.originalTitle?.contains(keyword, true) == true }
            _filterText.value = filter

        }

    }

}