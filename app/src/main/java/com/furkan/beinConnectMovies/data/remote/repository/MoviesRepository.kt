package com.furkan.beinConnectMovies.data.remote.repository

import com.furkan.beinConnectMovies.data.remote.dto.Callback
import com.furkan.beinConnectMovies.utils.Sources.API_KEY
import com.furkan.beinConnectMovies.utils.model.ErrorModel
import com.furkan.beinConnectMovies.data.remote.dto.ApiClient

class MoviesRepository {

    suspend fun getMovies(id:Int): Callback<Any> {
        return try {
            val movies = ApiClient.run().getMovies(
                API_KEY,
                "popularity.desc",
                false,
                include_video = false,
                page = 1,
                withGenres = id,
            )
            if (movies.results.isNullOrEmpty()) {
                Callback.Failure(ErrorModel.FAIL.code)
            } else {
                Callback.Success(movies)
            }
        } catch (e: Exception) {
            Callback.Failure(ErrorModel.FAIL.code)
        }
    }
}