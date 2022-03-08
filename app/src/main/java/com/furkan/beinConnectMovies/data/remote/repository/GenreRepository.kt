package com.furkan.beinConnectMovies.data.remote.repository

import com.furkan.beinConnectMovies.data.remote.dto.Callback
import com.furkan.beinConnectMovies.utils.Sources.API_KEY
import com.furkan.beinConnectMovies.utils.Sources.LANGUAGE
import com.inferne.teamso.data.remote.dto.ApiClient

class GenreRepository : Callback<Any>() {

    suspend fun getMovieGenres(): Callback<Any> {
        return try {
            val genre = ApiClient.run().getMovieGenres(
                API_KEY,
                LANGUAGE
            )
            if (genre.success == false) {
                Failure(genre.status_message.toString())
            } else {
                Success(genre)
            }
        } catch (e: Exception) {
            Failure("")
        }
    }

}