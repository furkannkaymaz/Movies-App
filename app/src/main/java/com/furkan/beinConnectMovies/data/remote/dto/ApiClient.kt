package com.inferne.teamso.data.remote.dto

import com.furkan.beinConnectMovies.data.remote.dto.ApiServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BaseWebservice = "https://api.themoviedb.org/3/"

object ApiClient {

    private val httpBuilder = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))  /// show all JSON in logCat

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BaseWebservice)
        .build()
        .create(ApiServices::class.java)

    fun run() = retrofit

}